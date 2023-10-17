package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeVersionOverview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class GetEventTypeVersionsService implements GetEventTypeVersionsUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;
    private final FetchEventTypesPort fetchEventTypesPort;

    @Override
    public Response execute(Request request) {
        var managerID = fetchManagerID(request.getManagerIdentifier());
        var roomID = fetchRoomID(request.getRoomIdentifier(), managerID);
        var eventTypeID = fetchEventTypeID(request.getEventTypeIdentifier(), roomID);
        var eventTypeVersions = fetchEventTypeVersions(eventTypeID);
        return new Response(eventTypeVersions);
    }

    private ManagerID fetchManagerID(ManagerIdentifier identifier) {
        return fetchManagersPort.fetchManagerID(identifier).orElseThrow(ManagerNotFoundException::new);
    }

    private RoomID fetchRoomID(RoomIdentifier identifier, ManagerID managerID) {
        return fetchRoomsPort.fetchRoomID(identifier, managerID).orElseThrow(RoomNotFoundException::new);
    }

    private EventTypeID fetchEventTypeID(EventTypeIdentifier identifier, RoomID roomID) {
        return fetchEventTypesPort.fetchEventTypeID(identifier, roomID).orElseThrow(EventTypeNotFoundException::new);
    }

    private List<EventTypeVersionOverview> fetchEventTypeVersions(EventTypeID eventTypeID) {
        return fetchEventTypesPort.fetchEventTypeVersionOverviews(eventTypeID);
    }
}
