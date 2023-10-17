package com.govstack.information_mediator.pubsub.messaging.domain.usecase.pushing;

import com.fasterxml.jackson.databind.JsonNode;
import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.messaging.domain.entity.PushEventMessage;
import com.govstack.information_mediator.pubsub.messaging.domain.port.EventsDeliveryPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.EventsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchSubscriptionsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PublishEventMessagesPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PublishedMessagesPort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PUSH;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.ACTIVE;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
class PushEventMessagesService implements PushEventMessagesUseCase {

    private final EventsPort eventsPort;
    private final JsonService jsonService;
    private final FetchRoomsPort fetchRoomsPort;
    private final PublishedMessagesPort publishedMessagesPort;
    private final FetchSubscriptionsPort fetchSubscriptionsPort;
    private final EventsDeliveryPort eventsDeliveryPort;
    private final PublishEventMessagesPort publishEventMessagesPort;

    @Override
    public void execute(Request request) {
        var message = request.eventMessage();

        var hasEventRecord = eventsPort.containsEvent(message.getEventId());
        if (!hasEventRecord) {
            return;
        }

        var hasBeenPublished = publishedMessagesPort.isEventPublished(message.getEventId(), message.getSubscriptionId());
        if (!hasBeenPublished) {
            return;
        }

        var hasBeenDelivered = publishedMessagesPort.isEventDelivered(message.getEventId(), message.getSubscriptionId());
        if (hasBeenDelivered) {
            return;
        }

        var subscription = fetchSubscription(message.getSubscriptionId());
        if (subscription == null) {
            return;
        }

        var room = fetchRoomsPort.fetchRoom(message.getRoomId()).orElse(null);
        if (room == null) {
            return;
        }

        try {
           handleDelivery(subscription, message);
        } catch (Exception e) {
            log.error("Failed to push event {} for subscription: {}", message.getEventId(), subscription.getId(), e);
            onFailedDelivery(room, subscription, message);
        }
    }

    private Subscription fetchSubscription(UUID subscriptionId) {
        return fetchSubscriptionsPort.fetchSubscription(subscriptionId)
            .filter(subscription -> subscription.getStatus() == ACTIVE)
            .filter(subscription -> subscription.getParameters().getMethod() == PUSH)
            .orElse(null);
    }

    private JsonNode convertToJson(String payload) {
        return jsonService.readTree(payload);
    }

    private void handleDelivery(Subscription subscription, PushEventMessage message) {
        eventsDeliveryPort.deliverEventPayload(subscription, convertToJson(message.getPayload()));
        publishedMessagesPort.markEventIsDelivered(message.getEventId(), subscription.getId());
    }

    private void onFailedDelivery(Room room, Subscription subscription, PushEventMessage message) {
        publishedMessagesPort.markEventIsPublished(message.getEventId(), subscription.getId());
        publishEventMessagesPort.republishEvent(message, subscription, room);
    }
}
