package com.govstack.information_mediator.pubsub.management.domain.usecase.publisher;

import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.PublisherIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchPublishersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.view.PublisherView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class GetPublishersService implements GetPublishersUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;
    private final FetchPublishersPort fetchPublishersPort;

    @Override
    public Response execute(Request request) {
        var room = fetchRoom(request.room());

        var manager = fetchManager(request.member().asManager(), room);
        if (manager.isPresent()) {
            return new Response(fetchPublishersView(room, request.pageRequest()));
        }

        return new Response(fetPublisherOverviews(request.member().asPublisher(), room, request.pageRequest()));
    }

    private Room fetchRoom(RoomIdentifier room) {
        return fetchRoomsPort.fetchRoom(room.getIdentifier()).orElseThrow(RoomNotFoundException::new);
    }

    private Optional<ManagerID> fetchManager(ManagerIdentifier manager, Room room) {
        return fetchManagersPort.fetchManagerID(manager)
            .filter(managerId -> room.getManagerId().equals(managerId.getId()));
    }

    private Page<PublisherView> fetchPublishersView(Room room, PageRequest pageRequest) {
        return fetchPublishersPort.fetchPublishersView(new RoomID(room.getId()), pageRequest);
    }

    private Page<PublisherView> fetPublisherOverviews(PublisherIdentifier publisher, Room room, PageRequest pageRequest) {
        return fetchPublishersView(fetchPublishersPort.fetchPublisher(publisher, new RoomID(room.getId())), pageRequest);
    }

    private Page<PublisherView> fetchPublishersView(List<UUID> publisherIDs, PageRequest pageRequest) {
        return fetchPublishersPort.fetchPublishersView(publisherIDs, pageRequest);
    }
}
