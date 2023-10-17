package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.TerminateRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.room.TerminateRoomsUseCase.Request.ManagerData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.room.TerminateRoomsUseCase.Request.RoomData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class TerminateRoomsService implements TerminateRoomsUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;
    private final TerminateRoomsPort terminateRoomsPort;

    @Override
    @Transactional
    public void execute(Request request) {
        var manager = fetchManager(request.manager());
        var room = fetchRoom(request.room(), manager);
        terminateRoomsPort.terminate(room);
    }

    private Manager fetchManager(ManagerData managerData) {
        return fetchManagersPort.fetchManager(managerData.identifier()).orElseThrow(ManagerNotFoundException::new);
    }

    private Room fetchRoom(RoomData roomData, Manager manager) {
        return fetchRoomsPort.fetchRoom(roomData.identifier(), manager).orElseThrow(RoomNotFoundException::new);
    }
}
