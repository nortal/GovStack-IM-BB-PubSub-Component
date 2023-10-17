package com.govstack.information_mediator.pubsub.messaging.domain.usecase.pulling;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.SubscriptionNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.EventMessage;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.messaging.domain.port.EventsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchSubscriptionsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PublishedMessagesPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PullEventMessagesPort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PULL;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.ACTIVE;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
class PullEventMessageService implements PullEventMessagesUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchSubscriptionsPort fetchSubscriptionsPort;
    private final PullEventMessagesPort pullEventMessagesPort;
    private final EventsPort eventsPort;
    private final PublishedMessagesPort publishedMessagesPort;
    private final JsonService jsonService;

    @Override
    public Response execute(Request request) {
        var room = fetchRoom(request.roomIdentifier());
        var subscription = fetchSubscription(request.subscriptionId(), request.subscriptionIdentifier(), room);
        return pullEventMessagesPort.pullEventMessage(subscription)
            .map(event -> processEvent(event, subscription))
            .orElseGet(Response::empty);
    }

    private Room fetchRoom(String identifier) {
        return fetchRoomsPort.fetchRoomByIdentifier(identifier).orElseThrow(RoomNotFoundException::new);
    }

    private Subscription fetchSubscription(UUID subscriptionId, String identifier, Room room) {
        return fetchSubscriptionsPort.fetchSubscription(subscriptionId)
            .filter(subscription -> subscription.getIdentifier().equals(identifier))
            .filter(subscription -> subscription.getRoomId().equals(room.getId()))
            .filter(subscription -> subscription.getParameters().getMethod().equals(PULL))
            .filter(subscription -> subscription.getStatus() == ACTIVE)
            .orElseThrow(SubscriptionNotFoundException::new);
    }

    private Response processEvent(EventMessage event, Subscription subscription) {
        var isGhost = !eventsPort.containsEvent(event.getEventId());
        if (isGhost) {
            return new Response(null, event.isHasAnother());
        }

        var hasBeenPublished = publishedMessagesPort.isEventPublished(event.getEventId(), subscription.getId());
        if (!hasBeenPublished) {
            return new Response(null, event.isHasAnother());
        }

        var isDelivered = publishedMessagesPort.isEventDelivered(event.getEventId(), subscription.getId());
        if (isDelivered) {
            return new Response(null, event.isHasAnother());
        }

        publishedMessagesPort.markEventIsDelivered(event.getEventId(), subscription.getId());

        var payload = jsonService.readTree(event.getPayload());
        return new Response(payload, event.isHasAnother());
    }
}
