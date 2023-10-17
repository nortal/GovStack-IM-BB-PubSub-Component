package com.govstack.information_mediator.pubsub.management.jdbc;

import com.govstack.information_mediator.pubsub.commons.exception.DuplicateResourceException;
import com.govstack.information_mediator.pubsub.commons.exception.ResourceNotFoundException;
import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.commons.schema.SchemaProvider;
import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import com.govstack.information_mediator.pubsub.domain.entity.EventType.Version;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeVersionID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.DeleteEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeView;
import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeVersionOverview;
import com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.EventTypeVersionsRecord;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.EventTypesRecord;
import com.networknt.schema.JsonSchema;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.JSONB;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.EVENT_TYPE_ALREADY_EXISTS;
import static com.govstack.information_mediator.pubsub.commons.validation.Violation.EVENT_TYPE_VERSION_ALREADY_EXISTS;
import static com.govstack.information_mediator.pubsub.commons.validation.Violation.EVENT_TYPE_VERSION_NOT_FOUND;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.ACTION;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.AT;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.BY;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.JournalAction.CREATED;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.JournalAction.MODIFIED;
import static com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory.isNotDeleted;
import static com.govstack.information_mediator.pubsub.shared.jooq.tables.EventTypeVersions.EVENT_TYPE_VERSIONS;
import static com.govstack.information_mediator.pubsub.shared.jooq.tables.EventTypes.EVENT_TYPES;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.jsonbGetAttributeAsText;
import static org.jooq.impl.DSL.jsonbGetElement;
import static org.jooq.impl.DSL.when;

@Component
@RequiredArgsConstructor
class EventTypesGateway implements FetchEventTypesPort, CreateEventTypesPort, DeleteEventTypesPort,
    OnRoomsCascadeDeletionListener {

    private final Clock clock;
    private final DSLContext dsl;
    private final RecordFactory<EventTypesRecord> eventTypesRecordFactory;
    private final RecordFactory<EventTypeVersionsRecord> eventTypeVersionsRecordFactory;
    private final List<OnEventTypesCascadeDeletionListener> onEventTypesCascadeDeletionListeners;

    @Override
    public Optional<EventType> fetchEventType(String identifier, Room room) {
        var condition = EVENT_TYPES.IDENTIFIER.eq(identifier).and(EVENT_TYPES.ROOM_ID.eq(room.getId()));
        return eventTypesRecordFactory.loadUsingCondition(EventTypesRecord.class, condition)
            .map(this::toDomainEntity)
            .map(this::enrichWithVersion);
    }

    @Override
    public Optional<EventTypeID> fetchEventTypeID(EventTypeIdentifier eventTypeIdentifier, RoomID roomID) {
        var condition = EVENT_TYPES.IDENTIFIER.eq(eventTypeIdentifier.getIdentifier()).and(EVENT_TYPES.ROOM_ID.eq(roomID.getId()));
        return eventTypesRecordFactory.retrieveField(EVENT_TYPES, EVENT_TYPES.ID, condition).map(EventTypeID::new);
    }

    @Override
    public Optional<EventTypeVersionID> fetchEventTypeVersionID(EventTypeID eventTypeID, Integer versionNo) {
        var condition = EVENT_TYPE_VERSIONS.EVENT_TYPE_ID.eq(eventTypeID.getId()).and(EVENT_TYPE_VERSIONS.VERSION.eq(versionNo));
        return eventTypeVersionsRecordFactory.retrieveField(EVENT_TYPE_VERSIONS, EVENT_TYPE_VERSIONS.ID, condition).map(EventTypeVersionID::new);
    }

    @Override
    public Integer countEventTypeVersions(EventTypeID eventTypeID) {
        var condition = EVENT_TYPE_VERSIONS.EVENT_TYPE_ID.eq(eventTypeID.getId()).and(isNotDeleted(EVENT_TYPE_VERSIONS));
        return dsl.fetchCount(EVENT_TYPE_VERSIONS, condition);
    }

    @Override
    public Page<EventTypeView> fetchEventTypeViews(RoomID roomID, PageRequest pageRequest) {
        var createdAt = when(
            jsonbGetAttributeAsText(jsonbGetElement(EVENT_TYPES.JOURNAL, 0), ACTION).eq(CREATED.name()),
            jsonbGetAttributeAsText(jsonbGetElement(EVENT_TYPES.JOURNAL, 0), AT).cast(Instant.class)
        ).otherwise((Instant) null).as("created_at");

        var createdBy = when(
            jsonbGetAttributeAsText(jsonbGetElement(EVENT_TYPES.JOURNAL, 0), ACTION).eq(CREATED.name()),
            jsonbGetAttributeAsText(jsonbGetElement(EVENT_TYPES.JOURNAL, 0), BY).cast(String.class)
        ).otherwise((String) null).as("created_by");

        var versions = count(EVENT_TYPE_VERSIONS.ID).as("versions");

        var condition = EVENT_TYPES.ROOM_ID.eq(roomID.getId())
            .and(isNotDeleted(EVENT_TYPES))
            .and(isNotDeleted(EVENT_TYPE_VERSIONS));

        var query = dsl.select(EVENT_TYPES.ID, EVENT_TYPES.IDENTIFIER, createdAt, createdBy, versions)
            .from(EVENT_TYPES)
            .leftJoin(EVENT_TYPE_VERSIONS).on(EVENT_TYPE_VERSIONS.EVENT_TYPE_ID.eq(EVENT_TYPES.ID))
            .where(condition)
            .groupBy(EVENT_TYPES.ID, EVENT_TYPES.IDENTIFIER, createdAt, createdBy);

        // Calculate total number of pages
        int totalNumberOfElements = (int) query.stream().count();

        Field<?> sortByField = EVENT_TYPES.IDENTIFIER;
        if ("createdAt".equals(pageRequest.getSortBy())) {
            sortByField = createdAt;
        } else if ("createdBy".equals(pageRequest.getSortBy())) {
            sortByField = createdBy;
        } else if ("versions".equals(pageRequest.getSortBy())) {
            sortByField = versions;
        }

        var list = query
            .orderBy(pageRequest.isDescendingOrder() ? sortByField.desc() : sortByField.asc())
            .limit(pageRequest.getMaxItemsPerPage())
            .offset(pageRequest.getMaxItemsPerPage() * pageRequest.getPageOffset())
            .fetch()
            .map(record -> EventTypeView.builder()
                .id(record.get(EVENT_TYPES.ID))
                .identifier(record.get(EVENT_TYPES.IDENTIFIER))
                .createdAt(record.get(createdAt))
                .createdBy(record.get(createdBy))
                .versions(record.get(versions))
                .build());

        return Page.<EventTypeView>builder()
            .content(list)
            .totalNumberOfElements(totalNumberOfElements)
            .maxItemsPerPage(pageRequest.getMaxItemsPerPage())
            .currentPageNumber(pageRequest.getPageOffset())
            .currentPageNumberOfElements(list.size())
            .build();
    }

    @Override
    public List<EventTypeVersionOverview> fetchEventTypeVersionOverviews(EventTypeID eventTypeID) {
        var id = EVENT_TYPE_VERSIONS.ID.as("id");
        var eventTypeId = EVENT_TYPE_VERSIONS.EVENT_TYPE_ID.as("event_type_id");
        var version = EVENT_TYPE_VERSIONS.VERSION.as("version");
        var jsonSchema = EVENT_TYPE_VERSIONS.JSON_SCHEMA.as("json_schema");

        var createdAt = when(
            jsonbGetAttributeAsText(jsonbGetElement(EVENT_TYPE_VERSIONS.JOURNAL, 0), ACTION).eq(CREATED.name()),
            jsonbGetAttributeAsText(jsonbGetElement(EVENT_TYPE_VERSIONS.JOURNAL, 0), AT).cast(Instant.class)
        ).otherwise((Instant) null).as("created_at");

        var createdBy = when(
            jsonbGetAttributeAsText(jsonbGetElement(EVENT_TYPE_VERSIONS.JOURNAL, 0), ACTION).eq(CREATED.name()),
            jsonbGetAttributeAsText(jsonbGetElement(EVENT_TYPE_VERSIONS.JOURNAL, 0), BY).cast(String.class)
        ).otherwise((String) null).as("created_by");

        var modifiedAt = when(
            jsonbGetAttributeAsText(jsonbGetElement(EVENT_TYPE_VERSIONS.JOURNAL, -1), ACTION).eq(MODIFIED.name()),
            jsonbGetAttributeAsText(jsonbGetElement(EVENT_TYPE_VERSIONS.JOURNAL, -1), AT).cast(Instant.class)
        ).otherwise((Instant) null).as("modified_at");

        var modifiedBy = when(
            jsonbGetAttributeAsText(jsonbGetElement(EVENT_TYPE_VERSIONS.JOURNAL, -1), ACTION).eq(MODIFIED.name()),
            jsonbGetAttributeAsText(jsonbGetElement(EVENT_TYPE_VERSIONS.JOURNAL, -1), BY).cast(String.class)
        ).otherwise((String) null).as("modified_by");

        var condition = eventTypeId.eq(eventTypeID.getId()).and(isNotDeleted(EVENT_TYPE_VERSIONS));

        return dsl.select(id, eventTypeId, version, jsonSchema, createdAt, createdBy, modifiedBy, modifiedAt)
            .from(EVENT_TYPE_VERSIONS)
            .where(condition)
            .orderBy(version.desc())
            .fetch()
            .map(record -> EventTypeVersionOverview.builder()
                .id(record.get(id))
                .eventTypeId(record.get(eventTypeId))
                .versionNo(record.get(version))
                .jsonSchema(record.get(jsonSchema).data())
                .createdAt(record.get(createdAt))
                .createdBy(record.get(createdBy))
                .modifiedAt(record.get(modifiedAt))
                .modifiedBy(record.get(modifiedBy))
                .build());
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

    @Override
    public List<String> fetchEventTypeIdentifiers(RoomID roomID) {
        return dsl.select(EVENT_TYPES.IDENTIFIER)
            .from(EVENT_TYPES)
            .where(EVENT_TYPES.ROOM_ID.eq(roomID.getId()).and(isNotDeleted(EVENT_TYPES)))
            .orderBy(EVENT_TYPES.IDENTIFIER.asc())
            .fetch()
            .map(record -> record.get(EVENT_TYPES.IDENTIFIER));
    }

    @Override
    @Transactional
    public void createEventType(EventType eventType) {
        try {
            persistEventType(eventType);
        } catch (DuplicateKeyException e) {
            throw new DuplicateResourceException(EVENT_TYPE_ALREADY_EXISTS, e);
        }
    }

    @Override
    public void createEventTypeVersion(Version version, EventTypeID eventTypeID) {
        try {
            persistVersion(version, eventTypeID.getId());
        } catch (DuplicateKeyException e) {
            throw new DuplicateResourceException(EVENT_TYPE_VERSION_ALREADY_EXISTS, e);
        }
    }

    private void persistEventType(EventType eventType) {
        var eventTypeRecord = eventTypesRecordFactory.loadOrCreate(EventTypesRecord.class, null);
        eventTypeRecord.setIdentifier(eventType.getIdentifier());
        eventTypeRecord.setRoomId(eventType.getRoomId());
        eventTypeRecord.store();

        var eventTypeId = eventTypeRecord.getId();
        persistVersion(eventType.getVersion(), eventTypeId);
    }

    private void persistVersion(Version version, UUID eventTypeId) {
        var record = eventTypeVersionsRecordFactory.loadOrCreate(EventTypeVersionsRecord.class, null);
        record.setEventTypeId(eventTypeId);
        record.setVersion(version.getVersionNo());
        record.setJsonSchema(JSONB.jsonb(version.getJsonSchema().getSchemaNode().toString()));
        record.store();
    }

    @Override
    public void updateEvenTypeVersion(EventTypeVersionID versionID, JsonSchema jsonSchema) {
        var condition = EVENT_TYPE_VERSIONS.ID.eq(versionID.getId());
        var record = eventTypeVersionsRecordFactory.loadUsingCondition(EventTypeVersionsRecord.class, condition)
            .orElseThrow(() -> new ResourceNotFoundException(EVENT_TYPE_VERSION_NOT_FOUND));

        record.setJsonSchema(JSONB.jsonb(jsonSchema.getSchemaNode().toString()));
        record.store();
    }

    @Override
    @Transactional
    public void deleteEventType(EventType eventType) {
        var condition = EVENT_TYPES.ID.eq(eventType.getId());
        eventTypesRecordFactory.loadUsingCondition(EventTypesRecord.class, condition)
            .ifPresent(this::cascadeDeleteEventType);
    }

    @Override
    public void deleteEventTypeVersion(EventTypeVersionID eventTypeVersionID) {
        var condition = EVENT_TYPE_VERSIONS.ID.eq(eventTypeVersionID.getId());
        eventTypeVersionsRecordFactory.loadUsingCondition(EventTypeVersionsRecord.class, condition)
            .ifPresent(record -> {
                record.setDeletedAt(clock.instant());
                record.store();
            });
    }

    @Override
    @Transactional
    public void onRoomDeleted(UUID roomId) {
        var condition = EVENT_TYPES.ROOM_ID.eq(roomId);
        eventTypesRecordFactory.loadListUsingCondition(EventTypesRecord.class, condition)
            .forEach(this::cascadeDeleteEventType);
    }

    private void cascadeDeleteEventType(EventTypesRecord eventType) {
        eventType.setDeletedAt(clock.instant());
        eventType.store();

        deleteEventTypeVersions(eventType);
        onEventTypesCascadeDeletionListeners.forEach(listener -> listener.onEventTypeDeleted(eventType.getId()));
    }

    private void deleteEventTypeVersions(EventTypesRecord eventType) {
        var condition = EVENT_TYPE_VERSIONS.EVENT_TYPE_ID.eq(eventType.getId());
        eventTypeVersionsRecordFactory.loadListUsingCondition(EventTypeVersionsRecord.class, condition)
            .forEach(record -> {
                record.setDeletedAt(clock.instant());
                record.store();
            });
    }
}
