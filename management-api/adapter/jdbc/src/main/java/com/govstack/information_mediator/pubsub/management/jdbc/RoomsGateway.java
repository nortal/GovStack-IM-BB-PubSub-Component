package com.govstack.information_mediator.pubsub.management.jdbc;

import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.domain.view.RoomView;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateRoomPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.ModifyRoomPort;
import com.govstack.information_mediator.pubsub.management.domain.port.TerminateRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.view.RoomDetailedView;
import com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.ManagersRecord;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.RoomsRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.JSONB;
import org.jooq.SortField;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.ACTION;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.AT;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.BY;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.JournalAction.CREATED;
import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.MANAGERS;
import static com.govstack.information_mediator.pubsub.shared.jooq.tables.Rooms.ROOMS;
import static org.jooq.impl.DSL.jsonbGetAttributeAsText;
import static org.jooq.impl.DSL.jsonbGetElement;
import static org.jooq.impl.DSL.when;

@Slf4j
@Component
@RequiredArgsConstructor
class RoomsGateway implements
    FetchRoomsPort,
    CreateRoomPort,
    ModifyRoomPort,
    TerminateRoomsPort,
    OnManagerCascadeDeletionListener {

    private final Clock clock;
    private final DSLContext dsl;
    private final RecordFactory<RoomsRecord> roomsRecordFactory;
    private final List<OnRoomsCascadeDeletionListener> onRoomsCascadeDeletionListeners;

    @Override
    @Transactional
    public UUID createRoom(Room room) {
        var roomsRecord = roomsRecordFactory.loadOrCreate(RoomsRecord.class, null);
        roomsRecord.setManagerId(room.getManagerId());
        roomsRecord.setIdentifier(room.getIdentifier());
        roomsRecord.setConfiguration(JSONB.valueOf(RoomConfiguration.fromDomainEntity(room).toJsonString()));
        roomsRecord.store();

        return roomsRecord.getId();
    }

    @Override
    public Optional<Room> fetchRoom(String identifier) {
        return roomsRecordFactory.loadUsingCondition(RoomsRecord.class, ROOMS.IDENTIFIER.eq(identifier))
            .map(this::mapToDomainEntity);
    }

    @Override
    public Optional<Room> fetchRoom(String identifier, Manager manager) {
        var condition = ROOMS.IDENTIFIER.eq(identifier).and(ROOMS.MANAGER_ID.eq(manager.getId()));
        return roomsRecordFactory.loadUsingCondition(RoomsRecord.class, condition).map(this::mapToDomainEntity);
    }

    @Override
    public Optional<RoomDetailedView> fetchRoomDetailedView(String identifier, Manager manager) {
        var createdAt = when(
            jsonbGetAttributeAsText(jsonbGetElement(ROOMS.JOURNAL, 0), ACTION).eq(CREATED.name()),
            jsonbGetAttributeAsText(jsonbGetElement(ROOMS.JOURNAL, 0), AT).cast(Instant.class)
        ).otherwise((Instant) null).as("created_at");

        var createdBy = when(
            jsonbGetAttributeAsText(jsonbGetElement(ROOMS.JOURNAL, 0), ACTION).eq(CREATED.name()),
            jsonbGetAttributeAsText(jsonbGetElement(ROOMS.JOURNAL, 0), BY).cast(String.class)
        ).otherwise((String) null).as("created_by");

        var messageExpiration = jsonbGetAttributeAsText(ROOMS.CONFIGURATION, "messageExpiration").cast(Integer.class);
        var deliveryDelay = jsonbGetAttributeAsText(ROOMS.CONFIGURATION, "deliveryDelay").cast(Integer.class);
        var deliveryDelayMultiplier = jsonbGetAttributeAsText(ROOMS.CONFIGURATION, "deliveryDelayMultiplier").cast(Double.class);
        var deliveryAttempts = jsonbGetAttributeAsText(ROOMS.CONFIGURATION, "deliveryAttempts").cast(Integer.class);

        var condition = ROOMS.IDENTIFIER.eq(identifier)
                .and(ROOMS.MANAGER_ID.eq(manager.getId()))
                .and(ROOMS.DELETED_AT.isNull());

        var roomRecord = dsl.select(ROOMS.ID.as("id"), ROOMS.IDENTIFIER.as("identifier"), createdAt, createdBy, messageExpiration, deliveryDelay, deliveryDelayMultiplier, deliveryAttempts)
            .from(ROOMS)
            .where(condition)
            .fetchOne();

        if(roomRecord == null) {
            return Optional.empty();
        }

        return Optional.of(roomRecord.map(r -> RoomDetailedView.builder()
            .id(r.get(ROOMS.ID))
            .identifier(r.get(ROOMS.IDENTIFIER))
            .managerIdentifier(manager.getIdentifier())
            .configuration(RoomDetailedView.Configuration.builder()
                .deliveryAttempts(r.get(deliveryAttempts))
                .messageExpiration(r.get(messageExpiration))
                .deliveryDelay(r.get(deliveryDelay))
                .deliveryDelayMultiplier(r.get(deliveryDelayMultiplier))
                .build())
            .createdAt(r.get(createdAt))
            .createdBy(r.get(createdBy))
            .build()));
    }

    @Override
    public Optional<RoomID> fetchRoomID(RoomIdentifier roomIdentifier) {
        var condition = ROOMS.IDENTIFIER.eq(roomIdentifier.getIdentifier());
        return roomsRecordFactory.retrieveField(ROOMS, ROOMS.ID, condition).map(RoomID::new);
    }

    @Override
    public Optional<RoomID> fetchRoomID(RoomIdentifier roomIdentifier, ManagerID managerID) {
        var condition = ROOMS.IDENTIFIER.eq(roomIdentifier.getIdentifier()).and(ROOMS.MANAGER_ID.eq(managerID.getId()));
        return roomsRecordFactory.retrieveField(ROOMS, ROOMS.ID, condition).map(RoomID::new);
    }

    @Override
    public Page<RoomView> fetchRoomsByManager(Manager manager, PageRequest pageRequest) {
        return fetchRooms(
            ROOMS.DELETED_AT.isNull()
                .and(ROOMS.MANAGER_ID.eq(manager.getId())),
            pageRequest
        );
    }

    @Override
    public Page<RoomView> fetchAllRooms(PageRequest pageRequest) {
        return fetchRooms(ROOMS.DELETED_AT.isNull(), pageRequest);
    }

    private Page<RoomView> fetchRooms(Condition condition, PageRequest pageRequest) {
        int totalNumberOfElements = dsl.fetchCount(
            dsl.select().from(ROOMS).where(condition)
        );

        var list = dsl.select()
            .from(ROOMS)
            .join(MANAGERS).onKey()
            .where(condition)
            .orderBy(getOrderByTerms(pageRequest))
            .limit(pageRequest.getMaxItemsPerPage())
            .offset(pageRequest.getMaxItemsPerPage() * pageRequest.getPageOffset())
            .fetch()
            .stream()
            .map(roomRecord -> {
                RoomsRecord roomsRecord = roomRecord.into(ROOMS).into(RoomsRecord.class);
                ManagersRecord managersRecord = roomRecord.into(MANAGERS).into(ManagersRecord.class);
                return RoomView.builder()
                    .id(roomsRecord.getId())
                    .identifier(roomsRecord.getIdentifier())
                    .managerIdentifier(managersRecord.getIdentifier())
                    .build();
            })
            .toList();

        return Page.<RoomView>builder()
            .content(list)
            .totalNumberOfElements(totalNumberOfElements)
            .maxItemsPerPage(pageRequest.getMaxItemsPerPage())
            .currentPageNumber(pageRequest.getPageOffset())
            .currentPageNumberOfElements(list.size())
            .build();
    }

    private SortField<?> getOrderByTerms(PageRequest pageRequest) {
        if (StringUtils.equals(pageRequest.getSortBy(), "identifier")) {
            return pageRequest.isDescendingOrder() ? ROOMS.IDENTIFIER.desc() : ROOMS.IDENTIFIER.asc();
        }
        if (StringUtils.equals(pageRequest.getSortBy(), "managerIdentifier")) {
            return pageRequest.isDescendingOrder() ? ROOMS.MANAGER_ID.desc() : ROOMS.MANAGER_ID.asc();
        }
        return pageRequest.isDescendingOrder() ? ROOMS.ID.desc() : ROOMS.ID.asc();
    }

    @Override
    @Transactional
    public Room modifyRoom(Manager manager, String roomIdentifier, Room newRoomData) {
        var condition = ROOMS.IDENTIFIER.eq(roomIdentifier).and(ROOMS.MANAGER_ID.eq(manager.getId()));
        var roomsRecord = roomsRecordFactory.loadUsingCondition(RoomsRecord.class, condition)
            .orElseThrow((RoomNotFoundException::new));
        roomsRecord.setIdentifier(newRoomData.getIdentifier());
        roomsRecord.setManagerId(newRoomData.getManagerId());
        roomsRecord.setConfiguration(JSONB.valueOf(
            RoomConfiguration.fromDomainEntity(
                    Room.builder().configuration(newRoomData.getConfiguration()).build())
                .toJsonString()));
        roomsRecord.store();

        return mapToDomainEntity(roomsRecord);
    }

    private Room mapToDomainEntity(RoomsRecord roomsRecord) {
        return Room.builder()
            .id(roomsRecord.getId())
            .managerId(roomsRecord.getManagerId())
            .identifier(roomsRecord.getIdentifier())
            .configuration(RoomConfiguration.fromJsonString(roomsRecord.getConfiguration().data()).toDomainEntity())
            .build();
    }

    @Override
    @Transactional
    public void terminate(Room room) {
       cascadeDeleteRoom(room);
    }

    private void cascadeDeleteRoom(Room room) {
        roomsRecordFactory.loadUsingCondition(RoomsRecord.class, ROOMS.ID.eq(room.getId()))
            .ifPresent(roomsRecord -> {
                roomsRecord.setDeletedAt(clock.instant());
                roomsRecord.store();

                onRoomsCascadeDeletionListeners.forEach(listener -> listener.onRoomDeleted(room.getId()));
            });
    }

    @Override
    @Transactional
    public void onManagerDeleted(UUID managerId) {
        unsetManager(managerId);
    }

    private void unsetManager(UUID managerId) {
        roomsRecordFactory.loadListUsingCondition(RoomsRecord.class, ROOMS.MANAGER_ID.eq(managerId))
            .forEach(roomsRecord -> {
                roomsRecord.setManagerId(null);
                roomsRecord.store();
            });
    }
}
