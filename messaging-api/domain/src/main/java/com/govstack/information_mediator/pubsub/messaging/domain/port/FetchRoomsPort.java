package com.govstack.information_mediator.pubsub.messaging.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.Room;

import java.util.Optional;
import java.util.UUID;

public interface FetchRoomsPort {

    Optional<Room> fetchRoom(UUID id);

    Optional<Room> fetchRoomByIdentifier(String identifier);
}
