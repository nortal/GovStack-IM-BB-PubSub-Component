package com.govstack.information_mediator.pubsub.management.domain.usecase.publisher;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.PublisherNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchPublishersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.TerminatePublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class TerminatePublisherService implements TerminatePublisherUseCase {

    private final FetchManagersPort fetchManagersPort;
    private final FetchRoomsPort fetchRoomsPort;
    private final FetchPublishersPort fetchPublishersPort;
    private final TerminatePublisherPort terminatePublisherPort;

    @Override
    public void execute(Request request) {
        var manager = fetchManager(request.manager());
        var room = fetchRoom(request.room(), manager);
        terminatePublisher(request.publisher(), room);
    }

    private ManagerID fetchManager(ManagerIdentifier manager) {
        return fetchManagersPort.fetchManagerID(manager).orElseThrow(ManagerNotFoundException::new);
    }

    private RoomID fetchRoom(RoomIdentifier room, ManagerID managerID) {
        return fetchRoomsPort.fetchRoomID(room, managerID).orElseThrow(RoomNotFoundException::new);
    }

    private void terminatePublisher(PublisherID publisherID, RoomID roomID) {
        if (fetchPublishersPort.isPublisherInRoom(publisherID, roomID)) {
            terminatePublisherPort.terminate(publisherID);
        } else {
            throw new PublisherNotFoundException();
        }
    }
}
