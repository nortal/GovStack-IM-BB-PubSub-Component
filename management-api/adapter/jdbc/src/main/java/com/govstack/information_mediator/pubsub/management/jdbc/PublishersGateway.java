package com.govstack.information_mediator.pubsub.management.jdbc;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.Publisher;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.PublisherIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.CreatePublisherPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchPublishersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.PublisherConstraintsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.TerminatePublisherPort;
import com.govstack.information_mediator.pubsub.management.domain.view.PublisherView;
import com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.PublisherConstraintsRecord;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.PublishersRecord;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.ACTION;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.AT;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.BY;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.JournalAction.CREATED;
import static com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory.isNotDeleted;
import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.PUBLISHER_CONSTRAINTS;
import static com.govstack.information_mediator.pubsub.shared.jooq.tables.EventTypes.EVENT_TYPES;
import static com.govstack.information_mediator.pubsub.shared.jooq.tables.Publishers.PUBLISHERS;
import static org.jooq.impl.DSL.jsonbGetAttributeAsText;
import static org.jooq.impl.DSL.jsonbGetElement;
import static org.jooq.impl.DSL.when;

@Component
@RequiredArgsConstructor
class PublishersGateway implements FetchPublishersPort,
        CreatePublisherPort,
        TerminatePublisherPort,
        PublisherConstraintsPort,
        OnRoomsCascadeDeletionListener,
        OnEventTypesCascadeDeletionListener {

    private final Clock clock;
    private final DSLContext dsl;
    private final RecordFactory<PublishersRecord> publishersRecordFactory;
    private final RecordFactory<PublisherConstraintsRecord> publisherConstraintsRecordFactory;

    @Override
    public List<UUID> fetchPublisher(PublisherIdentifier publisher, RoomID roomID) {
        var condition = PUBLISHERS.IDENTIFIER.eq(publisher.getIdentifier()).and(PUBLISHERS.ROOM_ID.eq(roomID.getId()));
        return publishersRecordFactory.loadListUsingCondition(PublishersRecord.class, condition)
                .stream()
                .map(PublishersRecord::getId)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PublisherView> fetchPublishersView(RoomID roomID, PageRequest pageRequest) {
        var condition = PUBLISHERS.ROOM_ID.eq(roomID.getId()).and(isNotDeleted(PUBLISHERS));
        return fetchPublishersView(condition, pageRequest);
    }

    @Override
    public Page<PublisherView> fetchPublishersView(List<UUID> publisherIDs, PageRequest pageRequest) {
        var condition = PUBLISHERS.ID.in(publisherIDs).and(isNotDeleted(PUBLISHERS));
        return fetchPublishersView(condition, pageRequest);
    }

    private Page<PublisherView> fetchPublishersView(Condition condition, PageRequest pageRequest) {
        var id = PUBLISHERS.ID.as("id");
        var identifier = PUBLISHERS.IDENTIFIER.as("identifier");
        var identifierType = PUBLISHERS.IDENTIFIER_TYPE.as("identifier_type");

        var createdAt = when(
                jsonbGetAttributeAsText(jsonbGetElement(PUBLISHERS.JOURNAL, 0), ACTION).eq(CREATED.name()),
                jsonbGetAttributeAsText(jsonbGetElement(PUBLISHERS.JOURNAL, 0), AT).cast(Instant.class)
        ).otherwise((Instant) null).as("created_at");

        var createdBy = when(
                jsonbGetAttributeAsText(jsonbGetElement(PUBLISHERS.JOURNAL, 0), ACTION).eq(CREATED.name()),
                jsonbGetAttributeAsText(jsonbGetElement(PUBLISHERS.JOURNAL, 0), BY).cast(String.class)
        ).otherwise((String) null).as("created_by");

        var query = dsl.select(id, identifier, identifierType, createdAt, createdBy)
                .from(PUBLISHERS)
                .where(condition);

        // Calculate total number of pages
        int totalNumberOfElements = (int) query.stream().count();

        Field<?> orderByField = id;
        if ("identifier".equals(pageRequest.getSortBy())) {
            orderByField = identifier;
        } else if ("identifierType".equals(pageRequest.getSortBy())) {
            orderByField = identifierType;
        } else if ("createdAt".equals(pageRequest.getSortBy())) {
            orderByField = createdAt;
        } else if ("createdBy".equals(pageRequest.getSortBy())) {
            orderByField = createdBy;
        }

        var publishers = query
                .orderBy(pageRequest.isDescendingOrder() ? orderByField.desc() : orderByField.asc())
                .limit(pageRequest.getMaxItemsPerPage())
                .offset(pageRequest.getMaxItemsPerPage() * pageRequest.getPageOffset())
                .fetch()
                .map(record -> PublisherView.builder()
                        .id(record.get(id))
                        .identifier(record.get(identifier))
                        .identifierType(EnumUtils.getEnum(IdentifierType.class, record.get(identifierType)))
                        .createdAt(record.get(createdAt))
                        .createdBy(record.get(createdBy))
                        .build());

        enrichPublishersView(publishers);

        return Page.<PublisherView>builder()
                .content(publishers)
                .totalNumberOfElements(totalNumberOfElements)
                .maxItemsPerPage(pageRequest.getMaxItemsPerPage())
                .currentPageNumber(pageRequest.getPageOffset())
                .currentPageNumberOfElements(publishers.size())
                .build();
    }

    private void enrichPublishersView(List<PublisherView> publishers) {
        if (CollectionUtils.isEmpty(publishers)) {
            return;
        }
        enrichPublishersViewWithConstraints(publishers);
    }

    private void enrichPublishersViewWithConstraints(List<PublisherView> publishers) {
        var createdAt = PUBLISHER_CONSTRAINTS.CREATED_AT.as("created_at");
        var publisherId = PUBLISHER_CONSTRAINTS.PUBLISHER_ID.as("publisher_id");
        var eventTypeId = PUBLISHER_CONSTRAINTS.EVENT_TYPE_ID.as("event_type_id");
        var eventTypeIdentifier = EVENT_TYPES.IDENTIFIER.as("event_type_identifier");

        var publishersGroup = publishers.stream()
                .collect(Collectors.toMap(PublisherView::getId, Function.identity(), (first, second) -> second));

        var condition = isNotDeleted(PUBLISHER_CONSTRAINTS)
                .and(PUBLISHER_CONSTRAINTS.PUBLISHER_ID.in(publishersGroup.keySet()))
                .and(isNotDeleted(EVENT_TYPES));

        dsl.select(publisherId, eventTypeId, eventTypeIdentifier, createdAt)
                .from(PUBLISHER_CONSTRAINTS)
                .join(EVENT_TYPES).on(EVENT_TYPES.ID.eq(PUBLISHER_CONSTRAINTS.EVENT_TYPE_ID))
                .where(condition)
                .fetch()
                .forEach(record -> {
                    var constraints = publishersGroup.get(record.get(publisherId)).getConstraints();
                    constraints.add(PublisherView.Constraint.builder()
                            .eventTypeId(record.get(eventTypeId))
                            .eventTypeIdentifier(record.get(eventTypeIdentifier))
                            .createdAt(record.get(createdAt))
                            .build());
                });
    }

    @Override
    public boolean isPublisherInRoom(PublisherID publisherID, RoomID roomID) {
        var condition = PUBLISHERS.ID.eq(publisherID.getId())
                .and(PUBLISHERS.ROOM_ID.eq(roomID.getId()))
                .and(isNotDeleted(PUBLISHERS));
        return dsl.fetchCount(PUBLISHERS, condition) != 0;
    }

    @Override
    public PublisherID createPublisher(Publisher publisher) {
        var record = publishersRecordFactory.loadOrCreate(PublishersRecord.class, null);
        record.setIdentifier(publisher.getIdentifier());
        record.setIdentifierType(publisher.getIdentifierType().name());
        record.setRoomId(publisher.getRoomId());
        record.store();
        return new PublisherID(record.getId());
    }

    @Override
    @Transactional
    public void terminate(PublisherID publisherID) {
        var condition = PUBLISHERS.ID.eq(publisherID.getId());
        publishersRecordFactory.loadUsingCondition(PublishersRecord.class, condition)
                .ifPresent(this::cascadeDeletePublisher);
    }

    @Override
    public List<UUID> getPublisherEventTypeIds(PublisherID publisherID) {
        var condition = PUBLISHER_CONSTRAINTS.PUBLISHER_ID.eq(publisherID.getId()).and(isNotDeleted(PUBLISHER_CONSTRAINTS));
        return dsl.select(PUBLISHER_CONSTRAINTS.EVENT_TYPE_ID)
                .from(PUBLISHER_CONSTRAINTS)
                .where(condition)
                .fetch(PUBLISHER_CONSTRAINTS.EVENT_TYPE_ID);
    }

    @Override
    public void removeEventTypeConstraint(PublisherID publisherID, UUID eventTypeId) {
        var condition = PUBLISHER_CONSTRAINTS.PUBLISHER_ID.eq(publisherID.getId())
                .and(PUBLISHER_CONSTRAINTS.EVENT_TYPE_ID.eq(eventTypeId))
                .and(isNotDeleted(PUBLISHER_CONSTRAINTS));
        publisherConstraintsRecordFactory.loadUsingCondition(PublisherConstraintsRecord.class, condition)
                .ifPresent(record -> {
                    record.setDeletedAt(clock.instant());
                    record.store();
                });
    }

    @Override
    public void addEventTypeConstraint(PublisherID publisherID, UUID eventTypeId) {
        var record = publisherConstraintsRecordFactory.loadOrCreate(PublisherConstraintsRecord.class, null);
        record.setPublisherId(publisherID.getId());
        record.setEventTypeId(eventTypeId);
        record.setCreatedAt(clock.instant());
        record.store();
    }

    @Override
    @Transactional
    public void onRoomDeleted(UUID roomId) {
        var condition = PUBLISHERS.ROOM_ID.eq(roomId);
        publishersRecordFactory.loadListUsingCondition(PublishersRecord.class, condition)
                .forEach(this::cascadeDeletePublisher);
    }

    @Override
    @Transactional
    public void onEventTypeDeleted(UUID eventTypeId) {
        var condition = PUBLISHER_CONSTRAINTS.EVENT_TYPE_ID.eq(eventTypeId);
        deletePublisherConstraints(condition);
    }

    private void cascadeDeletePublisher(PublishersRecord publisher) {
        publisher.setDeletedAt(clock.instant());
        publisher.store();

        var condition = PUBLISHER_CONSTRAINTS.PUBLISHER_ID.eq(publisher.getId());
        deletePublisherConstraints(condition);
    }

    private void deletePublisherConstraints(Condition condition) {
        publisherConstraintsRecordFactory.loadListUsingCondition(PublisherConstraintsRecord.class, condition)
                .forEach(record -> {
                    record.setDeletedAt(clock.instant());
                    record.store();
                });
    }
}
