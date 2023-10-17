package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.exception.BusinessViolationException;
import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.ResourceNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeVersionID;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.DeleteEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.EVENT_TYPE_VERSION_DELETION_NOT_ALLOWED;
import static com.govstack.information_mediator.pubsub.commons.validation.Violation.EVENT_TYPE_VERSION_NOT_FOUND;

@Service
@RequiredArgsConstructor
class DeleteEventTypeVersionsService implements DeleteEventTypeVersionsUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;
    private final FetchEventTypesPort fetchEventTypesPort;
    private final DeleteEventTypesPort deleteEventTypesPort;

    @Override
    public void execute(Request request) {
        var managerID = fetchManagerID(request.getManagerIdentifier());
        var roomID = fetchRoomID(request.getRoomIdentifier(), managerID);
        var eventTypeID = fetchEventTypeID(request.getEventTypeIdentifier(), roomID);
        var eventTypeVersionID = fetchEventTypeVersionID(eventTypeID, request.getVersionNo());
        deleteEventTypeVersion(eventTypeID, eventTypeVersionID);
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

    private EventTypeVersionID fetchEventTypeVersionID(EventTypeID eventTypeID, Integer versionNo) {
        return fetchEventTypesPort.fetchEventTypeVersionID(eventTypeID, versionNo)
            .orElseThrow(() -> new ResourceNotFoundException(EVENT_TYPE_VERSION_NOT_FOUND));
    }

    private void deleteEventTypeVersion(EventTypeID eventTypeID, EventTypeVersionID eventTypeVersionID) {
        boolean hasAdditionalVersions = fetchEventTypesPort.countEventTypeVersions(eventTypeID) > 1;
        if (!hasAdditionalVersions) {
            throw new BusinessViolationException(EVENT_TYPE_VERSION_DELETION_NOT_ALLOWED);
        }
        deleteEventTypesPort.deleteEventTypeVersion(eventTypeVersionID);
    }
}
