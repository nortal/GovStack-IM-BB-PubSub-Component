package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
class ListRoomsService implements ListRoomsUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;

    @Override
    @Transactional
    public Response execute(Request request) {
        if (request.getMemberIdentifier() == null) {
            return new Response(fetchRoomsPort.fetchAllRooms(request.getPageRequest()));
        }

        var manager = fetchManager(request.getMemberIdentifier());
        return new Response(fetchRoomsPort.fetchRoomsByManager(manager, request.getPageRequest()));
    }

    private Manager fetchManager(String memberIdentifier) {
        return fetchManagersPort.fetchManager(memberIdentifier)
            .orElseThrow(ManagerNotFoundException::new);
    }
}
