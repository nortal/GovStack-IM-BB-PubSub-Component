package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.view.RoomDetailedView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
class GetRoomDetailsService implements GetRoomDetailsUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;

    @Override
    @Transactional
    public Response execute(Request request) {
        var manager = fetchManager(request.getManager().getIdentifier());
        var room = fetchRoom(request.getRoom().getIdentifier(), manager);
        return Response.of(room, manager);
    }

    private Manager fetchManager(String managerIdentifier) {
        return fetchManagersPort.fetchManager(managerIdentifier).orElseThrow(ManagerNotFoundException::new);
    }

    private RoomDetailedView fetchRoom(String roomIdentifier, Manager manager) {
        return fetchRoomsPort.fetchRoomDetailedView(roomIdentifier, manager).orElseThrow(RoomNotFoundException::new);
    }
}
