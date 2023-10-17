package com.govstack.information_mediator.pubsub.messaging.jdbc;

import com.govstack.information_mediator.pubsub.messaging.domain.port.PublishedMessagesPort;
import com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.PublishedMessagesRecord;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import java.time.Clock;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.shared.jooq.tables.PublishedMessages.PUBLISHED_MESSAGES;

@ApplicationScoped
@RequiredArgsConstructor
class PublishedMessagesGateway implements PublishedMessagesPort {

    private final Clock clock;
    private final DSLContext dsl;
    private final RecordFactory<PublishedMessagesRecord> publishedMessagesRecordFactory;

    @Override
    public UUID markEventIsPublished(UUID eventId, UUID subscriptionId) {
        var record = publishedMessagesRecordFactory.loadOrCreate(PublishedMessagesRecord.class, null);
        record.setEventId(eventId);
        record.setSubscriptionId(subscriptionId);
        record.setPublishedAt(clock.instant());
        record.store();
        return record.getId();
    }

    @Override
    public UUID markEventIsDelivered(UUID eventId, UUID subscriptionId) {
        var record = publishedMessagesRecordFactory.loadOrCreate(PublishedMessagesRecord.class, null);
        record.setEventId(eventId);
        record.setSubscriptionId(subscriptionId);
        record.setDeliveredAt(clock.instant());
        record.store();
        return record.getId();
    }

    public boolean isEventPublished(UUID eventId, UUID subscriptionId) {
        var condition = PUBLISHED_MESSAGES.EVENT_ID.eq(eventId)
            .and(PUBLISHED_MESSAGES.SUBSCRIPTION_ID.eq(subscriptionId))
            .and(PUBLISHED_MESSAGES.PUBLISHED_AT.isNotNull());
        return dsl.fetchCount(PUBLISHED_MESSAGES, condition) != 0;
    }

    @Override
    public boolean isEventDelivered(UUID eventId, UUID subscriptionId) {
        var condition = PUBLISHED_MESSAGES.EVENT_ID.eq(eventId)
            .and(PUBLISHED_MESSAGES.SUBSCRIPTION_ID.eq(subscriptionId))
            .and(PUBLISHED_MESSAGES.DELIVERED_AT.isNotNull());
        return dsl.fetchCount(PUBLISHED_MESSAGES, condition) != 0;
    }
}
