package com.govstack.information_mediator.pubsub.messaging.jdbc;

import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.Rooms;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.RoomsRecord;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
class RoomsGateway implements FetchRoomsPort {

    private final RecordFactory<RoomsRecord> roomsRecordFactory;

    @Override
    public Optional<Room> fetchRoom(UUID id) {
        return roomsRecordFactory.loadUsingCondition(RoomsRecord.class, Rooms.ROOMS.ID.eq(id))
            .map(this::mapToDomainEntity);
    }

    @Override
    public Optional<Room> fetchRoomByIdentifier(String identifier) {
        return roomsRecordFactory.loadUsingCondition(RoomsRecord.class, Rooms.ROOMS.IDENTIFIER.eq(identifier))
            .map(this::mapToDomainEntity);
    }

    private Room mapToDomainEntity(RoomsRecord record) {
        return Room.builder()
            .id(record.getId())
            .managerId(record.getManagerId())
            .identifier(record.getIdentifier())
            .configuration(RoomConfiguration.fromJsonString(record.getConfiguration().data()).toDomainEntity())
            .build();
    }
}
