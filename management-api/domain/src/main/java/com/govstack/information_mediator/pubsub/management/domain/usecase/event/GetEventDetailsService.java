package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.exception.EventNotFound;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventID;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.entity.EventDetails;
import com.govstack.information_mediator.pubsub.management.domain.port.EventDetailsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEventDetailsService implements GetEventDetailsUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;
    private final EventDetailsPort eventDetailsPort;

    @Override
    public Response execute(Request request) {
        var manager = fetchManager(request.manager());
        var room = fetchRoom(request.room(), manager);
        var eventDetails = fetchEvent(request.eventId(), room);
        return new Response(eventDetails);
    }

    private ManagerID fetchManager(ManagerIdentifier manager) {
        return fetchManagersPort.fetchManagerID(manager).orElseThrow(ManagerNotFoundException::new);
    }

    private RoomID fetchRoom(RoomIdentifier room, ManagerID manager) {
        return fetchRoomsPort.fetchRoomID(room, manager).orElseThrow(RoomNotFoundException::new);
    }

    private EventDetails fetchEvent(EventID eventID, RoomID roomID) {
        return eventDetailsPort.resolveEventDetails(eventID, roomID).orElseThrow(EventNotFound::new);
    }
}
