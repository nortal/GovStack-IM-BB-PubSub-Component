package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.ModifyRoomPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class ModifyRoomService implements ModifyRoomUseCase {

    private final FetchManagersPort fetchManagersPort;
    private final FetchRoomsPort fetchRoomsPort;
    private final ModifyRoomPort modifyRoomPort;

    @Override
    @Transactional
    public Response execute(Request request) {
        var manager = fetchManager(request.getManager().getIdentifier());
        Room currentRoom = fetchRoom(request.getRoom().getIdentifier(), manager);

        if (request.getNewRoomData() != null) {
            if (request.getNewRoomData().getIdentifier() != null) {
                currentRoom.setIdentifier(request.getNewRoomData().getIdentifier());
            }
            if (request.getNewRoomData().getConfiguration() != null) {
                Room.Configuration config = getModifiedConfiguration(request, currentRoom);
                currentRoom.setConfiguration(config);
            }
        }

        if (request.getNewManagerIdentifier() != null) {
            var newManager = fetchManager(request.getNewManagerIdentifier());
            currentRoom.setManagerId(newManager.getId());
            return Response.of(modifyRoom(manager, request.getRoom().getIdentifier(), currentRoom), newManager);
        }

        return Response.of(modifyRoom(manager, request.getRoom().getIdentifier(), currentRoom), manager);
    }

    private static Room.Configuration getModifiedConfiguration(Request request, Room newRoom) {
        Room.Configuration config = newRoom.getConfiguration();
        Room.Configuration newConfig = request.getNewRoomData().getConfiguration();

        if (newConfig.getMessageExpiration() != null) config.setMessageExpiration(newConfig.getMessageExpiration());
        if (newConfig.getDeliveryDelay() != null) config.setDeliveryDelay(newConfig.getDeliveryDelay());
        if (newConfig.getDeliveryDelayMultiplier() != null) config.setDeliveryDelayMultiplier(newConfig.getDeliveryDelayMultiplier());
        if (newConfig.getDeliveryAttempts() != null) config.setDeliveryAttempts(newConfig.getDeliveryAttempts());
        return config;
    }

    private Room modifyRoom(Manager manager, String roomIdentifier, Room newRoomData) {
        return modifyRoomPort.modifyRoom(manager, roomIdentifier, newRoomData);
    }

    private Manager fetchManager(String managerIdentifier) {
        return fetchManagersPort.fetchManager(managerIdentifier).orElseThrow(ManagerNotFoundException::new);
    }

    private Room fetchRoom(String roomIdentifier, Manager manager) {
        return fetchRoomsPort.fetchRoom(roomIdentifier, manager).orElseThrow(RoomNotFoundException::new);
    }
}
