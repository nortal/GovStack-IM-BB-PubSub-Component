package com.govstack.information_mediator.pubsub.management.jdbc;

import com.govstack.information_mediator.pubsub.domain.entity.Event;
import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.management.domain.entity.EventDetails;
import com.govstack.information_mediator.pubsub.management.domain.port.EventDetailsPort;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.EventTypeVersionsRecord;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.EventTypesRecord;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.EventsRecord;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.PublishedMessagesRecord;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.PublishersRecord;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.EVENTS;
import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.EVENT_TYPES;
import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.EVENT_TYPE_VERSIONS;
import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.PUBLISHED_MESSAGES;
import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.PUBLISHERS;
import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.SUBSCRIPTIONS;
import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.SUBSCRIPTION_STATUS;

@Service
@RequiredArgsConstructor
class EventDetailsGateway implements EventDetailsPort {

    private final DSLContext dsl;

    @Override
    public Optional<EventDetails> resolveEventDetails(EventID eventID, RoomID roomID) {
        return retrieveEvent(eventID, roomID).map(this::resolveEventDetails);
    }

    private Optional<Event> retrieveEvent(EventID eventID, RoomID roomID) {
        var condition = EVENTS.ID.eq(eventID.getId()).and(EVENTS.ROOM_ID.eq(roomID.getId()));
        return Optional.ofNullable(dsl.fetchOne(EVENTS, condition)).map(this::mapToEvent);
    }

    private Event mapToEvent(EventsRecord record) {
        return Event.builder()
            .id(record.getId())
            .roomId(record.getRoomId())
            .eventTypeId(record.getEventTypeId())
            .eventTypeVersionId(record.getEventTypeVersionId())
            .publisherId(record.getPublisherId())
            .correlationId(record.getCorrelationId())
            .createdAt(record.getCreatedAt())
            .build();
    }

    private EventDetails resolveEventDetails(Event event) {
        var eventDetails = new EventDetails();
        eventDetails.setEvent(mapToEvent(event));

        resolveEventType(event).ifPresent(eventDetails::setEventType);
        resolveEventTypeVersion(event).ifPresent(eventDetails::setEventTypeVersion);
        resolvePublisher(event).ifPresent(eventDetails::setPublisher);

        eventDetails.setSubscriptions(resolveSubscriptions(event));

        return eventDetails;
    }

    private EventDetails.Event mapToEvent(Event event) {
        return EventDetails.Event.builder()
            .id(event.getId())
            .correlationId(event.getCorrelationId())
            .createdAt(event.getCreatedAt())
            .build();
    }

    private Optional<EventDetails.EventType> resolveEventType(Event event) {
        var condition = EVENT_TYPES.ID.eq(event.getEventTypeId());
        return Optional.ofNullable(dsl.fetchOne(EVENT_TYPES, condition)).map(this::mapToEventType);
    }

    private EventDetails.EventType mapToEventType(EventTypesRecord record) {
        return EventDetails.EventType.builder()
            .id(record.getId())
            .identifier(record.getIdentifier())
            .deletedAt(record.getDeletedAt())
            .build();
    }

    private Optional<EventDetails.EventTypeVersion> resolveEventTypeVersion(Event event) {
        var condition = EVENT_TYPE_VERSIONS.ID.eq(event.getEventTypeVersionId());
        return Optional.ofNullable(dsl.fetchOne(EVENT_TYPE_VERSIONS, condition)).map(this::mapToEventTypeVersion);
    }

    private EventDetails.EventTypeVersion mapToEventTypeVersion(EventTypeVersionsRecord record) {
        return EventDetails.EventTypeVersion.builder()
            .id(record.getId())
            .versionNo(record.getVersion())
            .schema(record.getJsonSchema().data())
            .deletedAt(record.getDeletedAt())
            .build();
    }

    private Optional<EventDetails.Publisher> resolvePublisher(Event event) {
        var condition = PUBLISHERS.ID.eq(event.getPublisherId());
        return Optional.ofNullable(dsl.fetchOne(PUBLISHERS, condition)).map(this::mapToPublisher);
    }

    private EventDetails.Publisher mapToPublisher(PublishersRecord record) {
        return EventDetails.Publisher.builder()
            .id(record.getId())
            .identifier(record.getIdentifier())
            .identifierType(EnumUtils.getEnum(IdentifierType.class, record.getIdentifierType()))
            .deletedAt(record.getDeletedAt())
            .build();
    }

    private List<EventDetails.Subscription> resolveSubscriptions(Event event) {
        var deliveryGroups = groupEventDeliveriesBySubscriptionId(event);
        var condition = SUBSCRIPTIONS.ID.in(deliveryGroups.keySet());
        return dsl.selectFrom(SUBSCRIPTIONS)
            .where(condition)
            .fetch(record -> EventDetails.Subscription.builder()
                .id(record.getId())
                .identifier(record.getIdentifier())
                .identifierType(EnumUtils.getEnum(IdentifierType.class, record.getIdentifierType()))
                .status(retrieveSubscriptionStatus(record.getId()))
                .deliveries(deliveryGroups.get(record.getId()))
                .deliveryAttempts(deliveryGroups.get(record.getId()).size())
                .isDelivered(isEventDelivered(deliveryGroups.get(record.getId())))
                .deletedAt(record.getDeletedAt())
                .build());
    }

    private Map<UUID, List<EventDetails.EventDelivery>> groupEventDeliveriesBySubscriptionId(Event event) {
        var condition = PUBLISHED_MESSAGES.EVENT_ID.eq(event.getId());
        return dsl.selectFrom(PUBLISHED_MESSAGES)
            .where(condition)
            .fetchGroups(PUBLISHED_MESSAGES.SUBSCRIPTION_ID, this::mapToEventDelivery);
    }

    private EventDetails.EventDelivery mapToEventDelivery(PublishedMessagesRecord record) {
        return EventDetails.EventDelivery.builder()
            .publishedAt(record.getPublishedAt())
            .deliveredAt(record.getDeliveredAt())
            .build();
    }

    private Subscription.Status retrieveSubscriptionStatus(UUID subscriptionId) {
        var condition = SUBSCRIPTION_STATUS.SUBSCRIPTION_ID.eq(subscriptionId);
        return dsl.selectFrom(SUBSCRIPTION_STATUS)
            .where(condition)
            .orderBy(SUBSCRIPTION_STATUS.UPDATED_AT.desc())
            .limit(1)
            .fetchOne(record -> EnumUtils.getEnum(Subscription.Status.class, record.getStatus()));
    }

    private boolean isEventDelivered(List<EventDetails.EventDelivery> eventDeliveries) {
        return eventDeliveries.stream().anyMatch(delivery -> delivery.getDeliveredAt() != null);
    }
}
