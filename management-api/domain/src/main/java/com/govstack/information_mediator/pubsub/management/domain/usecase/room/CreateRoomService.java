package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomAlreadyExistsException;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateRoomPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.govstack.information_mediator.pubsub.management.domain.usecase.room.CreateRoomUseCase.Request.ManagerData;
import static com.govstack.information_mediator.pubsub.management.domain.usecase.room.CreateRoomUseCase.Request.RoomData;

@Component
@RequiredArgsConstructor
class CreateRoomService implements CreateRoomUseCase {

    private final CreateRoomPort createRoomPort;
    private final FetchManagersPort fetchManagersPort;
    private final FetchRoomsPort fetchRoomsPort;

    @Override
    @Transactional
    public Response execute(Request request) {
        var manager = fetchManager(request.getManager());
        if (isRoomAlreadyCreated(request.getRoom().getIdentifier(), manager)) {
            throw new RoomAlreadyExistsException();
        }
        return Response.of(createRoom(request.getRoom(), manager));
    }

    private Manager fetchManager(ManagerData managerData) {
        return fetchManagersPort.fetchManager(managerData.getIdentifier()).orElseThrow(ManagerNotFoundException::new);
    }

    private boolean isRoomAlreadyCreated(String identifier, Manager manager) {
        return fetchRoomsPort.fetchRoom(identifier, manager).isPresent();
    }

    private UUID createRoom(RoomData roomData, Manager manager) {
        var roomConfiguration = roomData.getConfiguration();
        var room = Room.builder()
            .managerId(manager.getId())
            .identifier(roomData.getIdentifier())
            .configuration(Room.Configuration.builder()
                .messageExpiration(roomConfiguration.getMessageExpiration())
                .deliveryDelay(roomConfiguration.getDeliveryDelay())
                .deliveryDelayMultiplier(roomConfiguration.getDeliveryDelayMultiplier())
                .deliveryAttempts(roomConfiguration.getDeliveryAttempts())
                .build())
            .build();
        return createRoomPort.createRoom(room);
    }
}
