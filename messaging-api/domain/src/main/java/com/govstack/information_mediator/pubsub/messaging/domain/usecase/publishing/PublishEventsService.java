package com.govstack.information_mediator.pubsub.messaging.domain.usecase.publishing;

import com.fasterxml.jackson.databind.JsonNode;
import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.PublisherNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.Event;
import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import com.govstack.information_mediator.pubsub.domain.entity.Publisher;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.PublisherIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.messaging.domain.port.EventsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchPublishersPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchSubscriptionsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PublishEventMessagesPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PublishedMessagesPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.ACTIVE;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
class PublishEventsService implements PublishEventsUseCase {

    private final Clock clock;
    private final EventsPort eventsPort;
    private final JsonService jsonService;
    private final FetchRoomsPort fetchRoomsPort;
    private final FetchPublishersPort fetchPublishersPort;
    private final FetchEventTypesPort fetchEventTypesPort;
    private final FetchSubscriptionsPort fetchSubscriptionsPort;
    private final PublishedMessagesPort publishedMessagesPort;
    private final PublishEventMessagesPort publishEventMessagesPort;

    @Override
    @Transactional
    public Response execute(Request request) {
        var room = fetchRoom(request.getRoom());
        var publisher = fetchPublisher(room, request.getPublisher());
        var eventType = fetchEventType(publisher, request.getEventType());

        validatePayload(eventType, request.getPayload());

        var event = createEvent(room, publisher, eventType);
        publishEvent(room, event, request.getPayload());

        return new Response(event.getId());
    }

    private Room fetchRoom(RoomIdentifier roomIdentifier) {
        return fetchRoomsPort.fetchRoomByIdentifier(roomIdentifier.getIdentifier()).orElseThrow(RoomNotFoundException::new);
    }

    private Publisher fetchPublisher(Room room, PublisherIdentifier publisherIdentifier) {
        return fetchPublishersPort.fetchPublisher(room, publisherIdentifier.getIdentifier()).orElseThrow(PublisherNotFoundException::new);
    }

    private EventType fetchEventType(Publisher publisher, EventTypeIdentifier eventTypeIdentifier) {
        return fetchEventTypesPort.fetchEventType(publisher, eventTypeIdentifier).orElseThrow(EventTypeNotFoundException::new);
    }

    private void validatePayload(EventType eventType, JsonNode payload) {
        var jsonSchema = eventType.getVersion().getJsonSchema();
        var validations = jsonSchema.validate(payload);
        boolean isCompatible = validations.isEmpty();
        if (!isCompatible) {
            throw new ApiException("The event payload is not compatible with the event type");
        }
    }

    private Event createEvent(Room room, Publisher publisher, EventType eventType) {
        var event =  Event.builder()
            .roomId(room.getId())
            .eventTypeId(eventType.getId())
            .eventTypeVersionId(eventType.getVersion().getId())
            .publisherId(publisher.getId())
            .correlationId(UUIDGenerator.randomUUID().toString())
            .createdAt(clock.instant())
            .build();

        var eventId = eventsPort.createEvent(event);
        event.setId(eventId);
        return event;
    }

    private void publishEvent(Room room, Event event,  JsonNode payload) {
        var subscriptions = fetchSubscriptions(event);
        if (subscriptions.isEmpty()) {
            return;
        }

        subscriptions.forEach(
            subscription -> publishedMessagesPort.markEventIsPublished(event.getId(), subscription.getId()));

        var payloadAsString = jsonService.writeValueAsString(payload);
        publishEventMessagesPort.publishEvent(room, subscriptions, event, payloadAsString);
    }

    private List<Subscription> fetchSubscriptions(Event event) {
        var roomID = new RoomID(event.getRoomId());
        var eventTypeID = new EventTypeID(event.getEventTypeId());
        return fetchSubscriptionsPort.fetchSubscriptions(roomID, eventTypeID)
            .stream()
            .filter(subscription -> subscription.getStatus() == ACTIVE)
            .collect(Collectors.toList());
    }
}
