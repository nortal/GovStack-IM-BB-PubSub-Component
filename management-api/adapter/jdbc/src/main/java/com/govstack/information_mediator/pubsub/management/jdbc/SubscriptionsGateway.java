package com.govstack.information_mediator.pubsub.management.jdbc;

import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.id.SubscriptionID;
import com.govstack.information_mediator.pubsub.domain.view.SubscriptionEventDeliveryView;
import com.govstack.information_mediator.pubsub.domain.view.SubscriptionView;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateSubscriptionsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.DeleteSubscriptionsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchSubscriptionsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.SubscriptionStatusPort;
import com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.SubscriptionEventViewRecord;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.SubscriptionStatusRecord;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.SubscriptionsRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.TERMINATED;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.ACTION;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.AT;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.BY;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.JournalAction.CREATED;
import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.EVENT_TYPES;
import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.SUBSCRIPTIONS;
import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.SUBSCRIPTION_EVENT_VIEW;
import static com.govstack.information_mediator.pubsub.shared.jooq.tables.SubscriptionStatus.SUBSCRIPTION_STATUS;
import static org.jooq.impl.DSL.jsonbGetAttributeAsText;
import static org.jooq.impl.DSL.jsonbGetElement;
import static org.jooq.impl.DSL.when;

@Slf4j
@Component
@RequiredArgsConstructor
class SubscriptionsGateway implements
    CreateSubscriptionsPort,
    DeleteSubscriptionsPort,
    SubscriptionStatusPort,
    FetchSubscriptionsPort,
    OnRoomsCascadeDeletionListener {

    private final Clock clock;
    private final DSLContext dsl;
    private final RecordFactory<SubscriptionsRecord> subscriptionsRecordFactory;
    private final RecordFactory<SubscriptionStatusRecord> subscriptionStatusRecordFactory;

    @Override
    @Transactional
    public UUID createSubscription(Subscription subscription) {
        var subscriptionsRecord = subscriptionsRecordFactory.loadOrCreate(SubscriptionsRecord.class, null);
        subscriptionsRecord.setRoomId(subscription.getRoomId());
        subscriptionsRecord.setEventTypeId(subscription.getEventTypeId());
        subscriptionsRecord.setIdentifierType(subscription.getIdentifierType().name());
        subscriptionsRecord.setIdentifier(subscription.getIdentifier());
        subscriptionsRecord.setParameters(JSONB.valueOf(SubscriptionParameters.fromDomainEntity(subscription).toJsonString()));
        subscriptionsRecord.store();

        setSubscriptionStatus(subscriptionsRecord.getId(), Status.PENDING);
        return subscriptionsRecord.getId();
    }

    @Override
    public Optional<Subscription> fetchSubscription(SubscriptionID subscriptionID, RoomID roomID) {
        var condition = SUBSCRIPTIONS.ID.eq(subscriptionID.getId())
            .and(SUBSCRIPTIONS.ROOM_ID.eq(roomID.getId()));

        return subscriptionsRecordFactory.loadUsingCondition(SubscriptionsRecord.class, condition)
            .map(this::toDomainEntity);
    }

    @Override
    public Optional<Subscription> fetchSubscription(UUID subscriptionId, String identifier, Room room) {
        var condition = SUBSCRIPTIONS.ID.eq(subscriptionId)
            .and(SUBSCRIPTIONS.IDENTIFIER.eq(identifier))
            .and(SUBSCRIPTIONS.ROOM_ID.eq(room.getId()));

        return subscriptionsRecordFactory.loadUsingCondition(SubscriptionsRecord.class, condition)
            .map(this::toDomainEntity);
    }

    @Override
    public Page<SubscriptionView> fetchAllSubscriptionViews(Room room, Subscription.Status status, PageRequest pageRequest) {
        var condition = SUBSCRIPTIONS.ROOM_ID.eq(room.getId())
            .and(EVENT_TYPES.DELETED_AT.isNull());

        if (status != null) {
            condition = condition.and(SUBSCRIPTION_STATUS.STATUS.eq(status.name()));
        }
        return fetchSubscriptionViews(condition, pageRequest);
    }

    @Override
    public Page<SubscriptionView> fetchAllSubscriptionViews(
        Room room,
        String identifier,
        Subscription.Status status,
        PageRequest pageRequest) {
        var condition = SUBSCRIPTIONS.ROOM_ID.eq(room.getId())
            .and(EVENT_TYPES.DELETED_AT.isNull())
            .and(SUBSCRIPTIONS.IDENTIFIER.eq(identifier));

        if (status != null) {
            condition = condition.and(SUBSCRIPTION_STATUS.STATUS.eq(status.name()));
        }
        return fetchSubscriptionViews(condition, pageRequest);
    }

    @Override
    public Page<SubscriptionEventDeliveryView> fetchSubscriptionEventDeliveries(
            Subscription subscription,
            PageRequest pageRequest,
            Instant fromDate,
            Instant toDate,
            UUID eventId,
            String eventTypeIdentifier,
            String deliveryStatus) {

        var condition = SUBSCRIPTION_EVENT_VIEW.SUBSCRIPTION_ID.eq(subscription.getId());
        condition = appendEventDeliveriesFilteringTerms(condition, fromDate, toDate, eventId, eventTypeIdentifier, deliveryStatus);

        var query = dsl.select()
            .from(SUBSCRIPTION_EVENT_VIEW)
            .where(condition);

        int totalNumberOfElements = (int) query.stream().count();

        Field<?> orderByField = SUBSCRIPTION_EVENT_VIEW.EVENT_ID;
        if ("eventTypeIdentifier".equals(pageRequest.getSortBy())) {
            orderByField = SUBSCRIPTION_EVENT_VIEW.EVENT_TYPE_IDENTIFIER;
        } else if ("createdAt".equals(pageRequest.getSortBy())) {
            orderByField = SUBSCRIPTION_EVENT_VIEW.EVENT_CREATED_AT;
        } else if ("deliveryStatus".equals(pageRequest.getSortBy())) {
            orderByField = SUBSCRIPTION_EVENT_VIEW.DELIVERY_STATUS;
        } else if ("deliveryAttempts".equals(pageRequest.getSortBy())) {
            orderByField = SUBSCRIPTION_EVENT_VIEW.DELIVERY_ATTEMPTS;
        }

        var list = query
            .orderBy(pageRequest.isDescendingOrder() ? orderByField.desc() : orderByField.asc())
            .limit(pageRequest.getMaxItemsPerPage())
            .offset(pageRequest.getMaxItemsPerPage() * pageRequest.getPageOffset())
            .fetch(record -> {
                SubscriptionEventViewRecord eventsRecord = record.into(SUBSCRIPTION_EVENT_VIEW).into(SubscriptionEventViewRecord.class);

                return SubscriptionEventDeliveryView.builder()
                    .eventId(eventsRecord.getEventId())
                    .eventCreatedAt(eventsRecord.getEventCreatedAt())
                    .deliveryAttempts(eventsRecord.getDeliveryAttempts())
                    .deliveryStatus(EnumUtils.getEnum(SubscriptionEventDeliveryView.DeliveryStatus.class, eventsRecord.getDeliveryStatus()))
                    .eventTypeIdentifier(eventsRecord.getEventTypeIdentifier())
                    .build();
            })
            .stream().toList();

        return Page.<SubscriptionEventDeliveryView>builder()
            .content(list)
            .totalNumberOfElements(totalNumberOfElements)
            .maxItemsPerPage(pageRequest.getMaxItemsPerPage())
            .currentPageNumber(pageRequest.getPageOffset())
            .currentPageNumberOfElements(list.size())
            .build();
    }

    private Condition appendEventDeliveriesFilteringTerms(Condition condition,
                                                          Instant fromDate,
                                                          Instant toDate,
                                                          UUID eventId,
                                                          String eventTypeIdentifier,
                                                          String deliveryStatus) {
        if (fromDate != null) {
            condition = condition.and(SUBSCRIPTION_EVENT_VIEW.EVENT_CREATED_AT.greaterOrEqual(fromDate));
        }
        if (toDate != null) {
            condition = condition.and(SUBSCRIPTION_EVENT_VIEW.EVENT_CREATED_AT.lessThan(toDate));
        }
        if (eventId != null) {
            condition = condition.and(SUBSCRIPTION_EVENT_VIEW.EVENT_ID.containsIgnoreCase(eventId));
        }
        if (!StringUtils.isEmpty(eventTypeIdentifier)) {
            condition = condition.and(SUBSCRIPTION_EVENT_VIEW.EVENT_TYPE_IDENTIFIER.containsIgnoreCase(eventTypeIdentifier));
        }
        if (!StringUtils.isEmpty(deliveryStatus)) {
            condition = condition.and(SUBSCRIPTION_EVENT_VIEW.DELIVERY_STATUS.eq(deliveryStatus));
        }
        return condition;
    }

    private Page<SubscriptionView> fetchSubscriptionViews(Condition condition, PageRequest pageRequest) {
        var id = SUBSCRIPTIONS.ID.as("id");
        var identifier = SUBSCRIPTIONS.IDENTIFIER.as("identifier");
        var parameters = SUBSCRIPTIONS.PARAMETERS.as("parameters");
        var eventTypeIdentifier = EVENT_TYPES.IDENTIFIER.as("event_type_identifier");

        var createdAt = when(
                jsonbGetAttributeAsText(jsonbGetElement(SUBSCRIPTIONS.JOURNAL, 0), ACTION).eq(CREATED.name()),
                jsonbGetAttributeAsText(jsonbGetElement(SUBSCRIPTIONS.JOURNAL, 0), AT).cast(Instant.class)
        ).otherwise((Instant) null).as("created_at");

        var createdBy = when(
                jsonbGetAttributeAsText(jsonbGetElement(SUBSCRIPTIONS.JOURNAL, 0), ACTION).eq(CREATED.name()),
                jsonbGetAttributeAsText(jsonbGetElement(SUBSCRIPTIONS.JOURNAL, 0), BY).cast(String.class)
        ).otherwise((String) null).as("created_by");

        var query = dsl.select(id, identifier, parameters, createdAt, createdBy, eventTypeIdentifier)
            .from(SUBSCRIPTIONS)
            .join(EVENT_TYPES).onKey()
            .join(SUBSCRIPTION_STATUS).on(
                SUBSCRIPTION_STATUS.SUBSCRIPTION_ID.eq(SUBSCRIPTIONS.ID)
                    .and(SUBSCRIPTION_STATUS.UPDATED_AT.eq(
                        DSL.select(DSL.max(SUBSCRIPTION_STATUS.UPDATED_AT))
                            .from(SUBSCRIPTION_STATUS)
                            .where(SUBSCRIPTION_STATUS.SUBSCRIPTION_ID.eq(SUBSCRIPTIONS.ID))
                    ))
            )
            .where(condition);

        int totalNumberOfElements = (int) query.stream().count();

        Field<?> orderByField = id;
        if ("identifier".equals(pageRequest.getSortBy())) {
            orderByField = identifier;
        } else if ("createdAt".equals(pageRequest.getSortBy())) {
            orderByField = createdAt;
        } else if ("status".equals(pageRequest.getSortBy())) {
            orderByField = SUBSCRIPTION_STATUS.STATUS;
        }

        var list = query
            .orderBy(pageRequest.isDescendingOrder() ? orderByField.desc() : orderByField.asc())
            .limit(pageRequest.getMaxItemsPerPage())
            .offset(pageRequest.getMaxItemsPerPage() * pageRequest.getPageOffset())
            .fetch()
            .map(record -> {
                SubscriptionsRecord subscriptionRecord = record.into(SUBSCRIPTIONS).into(SubscriptionsRecord.class);
                return SubscriptionView.builder()
                        .id(subscriptionRecord.getId())
                        .identifier(subscriptionRecord.getIdentifier())
                        .identifierType(EnumUtils.getEnum(IdentifierType.class, subscriptionRecord.getIdentifierType()))
                        .eventType(EventType.builder()
                                .identifier(record.get(eventTypeIdentifier))
                                .build())
                        .parameters(SubscriptionParameters.fromJsonString(subscriptionRecord.getParameters().data()).toDomainEntity())
                        .status(fetchSubscriptionStatus(subscriptionRecord.getId()))
                        .createdAt(record.get(createdAt))
                        .createdBy(record.get(createdBy))
                        .build();
            });

        return Page.<SubscriptionView>builder()
            .content(list)
            .totalNumberOfElements(totalNumberOfElements)
            .maxItemsPerPage(pageRequest.getMaxItemsPerPage())
            .currentPageNumber(pageRequest.getPageOffset())
            .currentPageNumberOfElements(list.size())
            .build();
    }

    @Override
    @Transactional
    public void terminateSubscription(UUID subscriptionId) {
        var condition = SUBSCRIPTIONS.ID.eq(subscriptionId);
        var subscription = subscriptionsRecordFactory.loadUsingCondition(SubscriptionsRecord.class, condition);

        subscription.ifPresent(record -> {
            record.setDeletedAt(clock.instant());
            record.store();

            setSubscriptionStatus(record.getId(), TERMINATED);
        });
    }

    @Override
    @Transactional
    public void setSubscriptionStatus(UUID subscriptionId, Status status) {
        var record = subscriptionStatusRecordFactory.loadOrCreate(SubscriptionStatusRecord.class, null);
        record.setSubscriptionId(subscriptionId);
        record.setStatus(status.name());
        record.setUpdatedAt(clock.instant());
        record.store();
    }

    private Subscription toDomainEntity(SubscriptionsRecord record) {
        return Subscription.builder()
            .id(record.getId())
            .roomId(record.getRoomId())
            .eventTypeId(record.getEventTypeId())
            .identifier(record.getIdentifier())
            .identifierType(EnumUtils.getEnum(IdentifierType.class, record.getIdentifierType()))
            .parameters(SubscriptionParameters.fromJsonString(record.getParameters().data()).toDomainEntity())
            .status(fetchSubscriptionStatus(record.getId()))
            .build();
    }

    @Override
    @Transactional
    public void onRoomDeleted(UUID roomId) {
        var condition = SUBSCRIPTIONS.ROOM_ID.eq(roomId);
        subscriptionsRecordFactory.loadListUsingCondition(SubscriptionsRecord.class, condition)
            .stream()
            .filter(this::canDeleteSubscription)
            .forEach(this::deleteSubscription);
    }

    private boolean canDeleteSubscription(SubscriptionsRecord subscription) {
        return fetchSubscriptionStatus(subscription.getId()) != TERMINATED;
    }

    private Status fetchSubscriptionStatus(UUID subscriptionId) {
        var record = dsl.select(SUBSCRIPTION_STATUS.asterisk())
            .from(SUBSCRIPTION_STATUS)
            .where(SUBSCRIPTION_STATUS.SUBSCRIPTION_ID.eq(subscriptionId))
            .orderBy(SUBSCRIPTION_STATUS.UPDATED_AT.desc())
            .limit(1)
            .fetchOneInto(SUBSCRIPTION_STATUS.getRecordType());

        if (record == null) {
            log.error("Status was not found for subscription with id [{}]", subscriptionId);
            throw new ApiException("Subscription without a status");
        }

        return EnumUtils.getEnum(Status.class, record.getStatus());
    }

    private void deleteSubscription(SubscriptionsRecord subscription) {
        subscription.setDeletedAt(clock.instant());
        subscription.store();

        setSubscriptionStatus(subscription.getId(), TERMINATED);
    }
}
