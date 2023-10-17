package com.govstack.information_mediator.pubsub.messaging.jdbc;

import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchSubscriptionsPort;
import com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.SubscriptionsRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.SUBSCRIPTIONS;
import static com.govstack.information_mediator.pubsub.shared.jooq.tables.SubscriptionStatus.SUBSCRIPTION_STATUS;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
class SubscriptionsGateway implements FetchSubscriptionsPort {

    private final DSLContext dsl;
    private final RecordFactory<SubscriptionsRecord> subscriptionsRecordFactory;

    @Override
    public Optional<Subscription> fetchSubscription(UUID id) {
        return subscriptionsRecordFactory.loadUsingCondition(SubscriptionsRecord.class, SUBSCRIPTIONS.ID.eq(id))
            .map(this::toDomainEntity)
            .map(this::enrichWithStatus);
    }

    @Override
    @Transactional
    public List<Subscription> fetchSubscriptions(RoomID roomID, EventTypeID eventTypeID) {
        var condition = SUBSCRIPTIONS.ROOM_ID.eq(roomID.getId()).and(SUBSCRIPTIONS.EVENT_TYPE_ID.eq(eventTypeID.getId()));
        return subscriptionsRecordFactory.loadListUsingCondition(SubscriptionsRecord.class, condition)
            .stream()
            .map(this::toDomainEntity)
            .map(this::enrichWithStatus)
            .toList();
    }

    private Subscription toDomainEntity(SubscriptionsRecord record) {
        return Subscription.builder()
            .id(record.getId())
            .roomId(record.getRoomId())
            .eventTypeId(record.getEventTypeId())
            .identifier(record.getIdentifier())
            .identifierType(EnumUtils.getEnum(IdentifierType.class, record.getIdentifierType()))
            .parameters(SubscriptionParameters.fromJsonString(record.getParameters().data()).toDomainEntity())
            .build();
    }

    private Subscription enrichWithStatus(Subscription subscription) {
        subscription.setStatus(fetchSubscriptionStatus(subscription.getId()));
        return subscription;
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
}
