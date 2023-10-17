package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.Room;

public interface TerminateRoomsPort {

    void terminate(Room room);
}
