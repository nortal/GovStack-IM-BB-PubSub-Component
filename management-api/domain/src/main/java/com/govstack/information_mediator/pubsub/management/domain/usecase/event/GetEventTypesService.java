package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetEventTypesService implements GetEventTypesUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;
    private final FetchEventTypesPort fetchEventTypesPort;

    @Override
    public Response execute(Request request) {
        var manager = fetchManager(request.manager());
        var room = fetchRoom(request.room(), manager);
        return new Response(fetchEventTypesPort.fetchEventTypeViews(room, request.pageRequest()));
    }

    private ManagerID fetchManager(ManagerIdentifier managerIdentifier) {
        return fetchManagersPort.fetchManagerID(managerIdentifier).orElseThrow(ManagerNotFoundException::new);
    }

    private RoomID fetchRoom(RoomIdentifier roomIdentifier, ManagerID managerID) {
        return fetchRoomsPort.fetchRoomID(roomIdentifier, managerID).orElseThrow(RoomNotFoundException::new);
    }
}
