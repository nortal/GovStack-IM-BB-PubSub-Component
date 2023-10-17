package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.domain.view.RoomView;
import com.govstack.information_mediator.pubsub.management.domain.view.RoomDetailedView;

import java.util.List;
import java.util.Optional;

public interface FetchRoomsPort {

    Optional<Room> fetchRoom(String identifier);

    Optional<Room> fetchRoom(String identifier, Manager manager);

    Optional<RoomDetailedView> fetchRoomDetailedView(String identifier, Manager manager);

    Optional<RoomID> fetchRoomID(RoomIdentifier roomIdentifier);

    Optional<RoomID> fetchRoomID(RoomIdentifier roomIdentifier, ManagerID managerID);

    Page<RoomView> fetchRoomsByManager(Manager manager, PageRequest pageRequest);

    Page<RoomView> fetchAllRooms(PageRequest pageRequest);
}
