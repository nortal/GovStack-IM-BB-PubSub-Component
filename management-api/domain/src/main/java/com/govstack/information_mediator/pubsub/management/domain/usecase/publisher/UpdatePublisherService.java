package com.govstack.information_mediator.pubsub.management.domain.usecase.publisher;

import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.PublisherNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchPublishersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.PublisherConstraintsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class UpdatePublisherService implements UpdatePublisherUseCase {

    private final FetchManagersPort fetchManagersPort;
    private final FetchRoomsPort fetchRoomsPort;
    private final FetchPublishersPort fetchPublishersPort;
    private final FetchEventTypesPort fetchEventTypesPort;
    private final PublisherConstraintsPort publisherConstraintsPort;

    @Override
    @Transactional
    public void execute(Request request) {
        var manager = fetchManager(request.manager());
        var room = fetchRoom(request.room(), manager);
        validatePublisherIsInRoom(request.publisherID(), room);
        var eventTypes = fetchEventTypes(new HashSet<>(request.eventTypes()), room);
        updateConstraints(request.publisherID(), eventTypes);
    }

    private ManagerID fetchManager(ManagerIdentifier manager) {
        return fetchManagersPort.fetchManagerID(manager).orElseThrow(ManagerNotFoundException::new);
    }

    private RoomID fetchRoom(RoomIdentifier room, ManagerID managerID) {
        return fetchRoomsPort.fetchRoomID(room, managerID).orElseThrow(RoomNotFoundException::new);
    }

    private void validatePublisherIsInRoom(PublisherID publisherID, RoomID roomID) {
        boolean isPublisherInRoom = fetchPublishersPort.isPublisherInRoom(publisherID, roomID);
        if (!isPublisherInRoom) {
            throw new PublisherNotFoundException();
        }
    }

    private List<UUID> fetchEventTypes(Set<String> eventTypes, RoomID roomID) {
        if (CollectionUtils.isEmpty(eventTypes)) {
            throw new ApiException("At least one event type is required for update the publisher");
        }
        return eventTypes.stream()
                .map(EventTypeIdentifier::new)
                .map(identifier -> fetchEventType(identifier, roomID))
                .map(EventTypeID::getId)
                .toList();
    }

    private EventTypeID fetchEventType(EventTypeIdentifier eventType, RoomID roomID) {
        return fetchEventTypesPort.fetchEventTypeID(eventType, roomID).orElseThrow(EventTypeNotFoundException::new);
    }

    private void updateConstraints(PublisherID publisherID, List<UUID> eventTypeIDs) {
        var constraints = publisherConstraintsPort.getPublisherEventTypeIds(publisherID);
        constraints.stream()
                .filter(eventTypeID -> !eventTypeIDs.contains(eventTypeID))
                .forEach(eventTypeID -> publisherConstraintsPort.removeEventTypeConstraint(publisherID, eventTypeID));
        eventTypeIDs.stream()
                .filter(eventTypeID -> !constraints.contains(eventTypeID))
                .forEach(eventTypeID -> publisherConstraintsPort.addEventTypeConstraint(publisherID, eventTypeID));
    }
}
