package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;

public interface ModifyRoomPort {

    Room modifyRoom(Manager manager, String roomIdentifier, Room newRoomData);

}
