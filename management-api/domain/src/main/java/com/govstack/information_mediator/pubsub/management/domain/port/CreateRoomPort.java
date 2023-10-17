package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.Room;

import java.util.UUID;

public interface CreateRoomPort {

    UUID createRoom(Room room);

}
