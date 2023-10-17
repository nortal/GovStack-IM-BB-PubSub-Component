package com.govstack.information_mediator.pubsub.messaging.jdbc;

import com.govstack.information_mediator.pubsub.commons.schema.SchemaProvider;
import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import com.govstack.information_mediator.pubsub.domain.entity.EventType.Version;
import com.govstack.information_mediator.pubsub.domain.entity.Publisher;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.EventTypes;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.PublisherConstraints;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.EventTypeVersionsRecord;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.EventTypesRecord;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import java.util.Optional;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory.isNotDeleted;
import static com.govstack.information_mediator.pubsub.shared.jooq.tables.EventTypeVersions.EVENT_TYPE_VERSIONS;
import static com.govstack.information_mediator.pubsub.shared.jooq.tables.EventTypes.EVENT_TYPES;
import static com.govstack.information_mediator.pubsub.shared.jooq.tables.PublisherConstraints.PUBLISHER_CONSTRAINTS;

@ApplicationScoped
@RequiredArgsConstructor
class EventTypesGateway implements FetchEventTypesPort {

    private static final EventTypes ET = EVENT_TYPES.as("et");
    private static final PublisherConstraints PC = PUBLISHER_CONSTRAINTS.as("pc");

    private final DSLContext dsl;
    private final RecordFactory<EventTypesRecord> eventTypesRecordFactory;

    @Override
    public Optional<EventType> fetchEventType(Publisher publisher, EventTypeIdentifier eventTypeIdentifier) {
        var joinCondition = ET.leftJoin(PC).on(PC.EVENT_TYPE_ID.eq(ET.ID).and(isNotDeleted(PC)));
        var condition = ET.IDENTIFIER.eq(eventTypeIdentifier.getIdentifier())
            .and(ET.ROOM_ID.eq(publisher.getRoomId()))
            .and(PC.PUBLISHER_ID.eq(publisher.getId()))
            .and(isNotDeleted(ET));
        return eventTypesRecordFactory.loadJoiningUsingCondition(ET, joinCondition, condition)
            .map(this::toDomainEntity)
            .map(this::enrichWithVersion);
    }

    private EventType toDomainEntity(EventTypesRecord record) {
        return EventType.builder()
            .id(record.getId())
            .roomId(record.getRoomId())
            .identifier(record.getIdentifier())
            .build();
    }

    private EventType enrichWithVersion(EventType eventType) {
        fetchEventTypeVersion(eventType.getId()).ifPresent(eventType::setVersion);
        return eventType;
    }

    private Optional<Version> fetchEventTypeVersion(UUID eventTypeId) {
        var condition = EVENT_TYPE_VERSIONS.EVENT_TYPE_ID.eq(eventTypeId).and(isNotDeleted(EVENT_TYPE_VERSIONS));
        var record = dsl.select(EVENT_TYPE_VERSIONS.asterisk())
            .from(EVENT_TYPE_VERSIONS)
            .where(condition)
            .orderBy(EVENT_TYPE_VERSIONS.VERSION.desc())
            .limit(1)
            .fetchOneInto(EVENT_TYPE_VERSIONS.getRecordType());
        return Optional.ofNullable(record).map(this::toDomainEntity);
    }

    private Version toDomainEntity(EventTypeVersionsRecord record) {
        SchemaProvider schemaProvider = new SchemaProvider(record.getJsonSchema().data());
        return Version.builder()
            .id(record.getId())
            .versionNo(record.getVersion())
            .jsonSchema(schemaProvider.getSchema())
            .build();
    }

}
