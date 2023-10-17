package com.govstack.information_mediator.pubsub.management.domain.usecase.publisher;

import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.Publisher;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.CreatePublisherPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.PublisherConstraintsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.publisher.CreatePublisherUseCae.Request.PublisherData;
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
class CreatePublisherService implements CreatePublisherUseCae {

    private final FetchManagersPort fetchManagersPort;
    private final FetchRoomsPort fetchRoomsPort;
    private final FetchEventTypesPort fetchEventTypesPort;
    private final CreatePublisherPort createPublisherPort;
    private final PublisherConstraintsPort publisherConstraintsPort;

    @Override
    @Transactional
    public Response execute(Request request) {
        var manager = fetchManager(request.manager());
        var room = fetchRoom(request.room(), manager);
        var publisher = createPublisher(request.publisher(), room);
        return new Response(publisher);
    }

    private ManagerID fetchManager(ManagerIdentifier manager) {
        return fetchManagersPort.fetchManagerID(manager).orElseThrow(ManagerNotFoundException::new);
    }

    private RoomID fetchRoom(RoomIdentifier room, ManagerID managerID) {
        return fetchRoomsPort.fetchRoomID(room, managerID).orElseThrow(RoomNotFoundException::new);
    }

    private PublisherID createPublisher(PublisherData publisherData, RoomID roomID) {
        var eventTypes = fetchEventTypes(new HashSet<>(publisherData.eventTypes()), roomID);

        var publisherID = createPublisherPort.createPublisher(Publisher.builder()
                .identifier(publisherData.identifier())
                .identifierType(publisherData.identifierType())
                .roomId(roomID.getId())
                .build());

        eventTypes.forEach(eventTypeID -> publisherConstraintsPort.addEventTypeConstraint(publisherID, eventTypeID));
        return publisherID;
    }

    private List<UUID> fetchEventTypes(Set<String> eventTypes, RoomID roomID) {
        if (CollectionUtils.isEmpty(eventTypes)) {
            throw new ApiException("At least one event type is required for creating a publisher");
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
}
