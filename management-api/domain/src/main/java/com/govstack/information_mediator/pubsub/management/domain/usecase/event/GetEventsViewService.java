package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventsViewPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.view.EventView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
class GetEventsViewService implements GetEventsViewUseCase {

    private final FetchManagersPort fetchManagersPort;
    private final FetchRoomsPort fetchRoomsPort;
    private final FetchEventsViewPort fetchEventsViewPort;

    @Override
    public Response execute(Request request) {
        var manager = fetchManager(request.getManager());
        var room = fetchRoom(request.getRoom(), manager);
        var events = fetchEvents(room, request.getPublisher(), request.getFromDate(), request.getToDate(),
                request.getPageRequest(),
                request.getPublisherIdentifier(), request.getEventTypeIdentifier(), request.getEventTypeVersion());
        return new Response(events);
    }

    private ManagerID fetchManager(ManagerIdentifier manager) {
        return fetchManagersPort.fetchManagerID(manager).orElseThrow(ManagerNotFoundException::new);
    }

    private RoomID fetchRoom(RoomIdentifier room, ManagerID manager) {
        return fetchRoomsPort.fetchRoomID(room, manager).orElseThrow(RoomNotFoundException::new);
    }

    private Page<EventView> fetchEvents(RoomID roomID, PublisherID publisherID, Instant fromDate, Instant toDate,
                                        PageRequest pageRequest,
                                        String publisherIdentifier, String eventTypeIdentifier, Integer eventTypeVersion) {
        if (publisherID == null) {
            return fetchEventsViewPort.fetchEvents(roomID, fromDate, toDate, pageRequest,
                    publisherIdentifier, eventTypeIdentifier, eventTypeVersion);
        } else {
            return fetchEventsViewPort.fetchEvents(roomID, publisherID, fromDate, toDate, pageRequest,
                    eventTypeIdentifier, eventTypeVersion);
        }
    }
}
