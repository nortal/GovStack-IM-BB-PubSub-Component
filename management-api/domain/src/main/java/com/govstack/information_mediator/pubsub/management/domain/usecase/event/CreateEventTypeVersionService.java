package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.exception.DuplicateResourceException;
import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.EventType.Version;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypeVersionUseCase.Request.VersionData;
import com.networknt.schema.JsonSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.EVENT_TYPE_VERSION_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
class CreateEventTypeVersionService implements CreateEventTypeVersionUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;
    private final FetchEventTypesPort fetchEventTypesPort;
    private final CreateEventTypesPort createEventTypesPort;
    private final EventTypeVersionSchemaService eventTypeVersionSchemaService;

    @Override
    public void execute(Request request) {
        var managerID = fetchManagerID(request.getManagerIdentifier());
        var roomID = fetchRoomID(request.getRoomIdentifier(), managerID);
        var eventTypeID = fetchEventTypeID(request.getEventTypeIdentifier(), roomID);
        createVersion(eventTypeID, request.getVersion());
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

    private void createVersion(EventTypeID eventTypeID, VersionData versionData) {
        var versionNo = versionData.getVersionNo();
        var versionExists = fetchEventTypesPort.fetchEventTypeVersionID(eventTypeID, versionNo).isPresent();
        if (versionExists) {
            throw new DuplicateResourceException(EVENT_TYPE_VERSION_ALREADY_EXISTS);
        }

        var version = Version.builder()
            .versionNo(versionNo)
            .jsonSchema(createJsonSchema(versionData.getJsonSchema()))
            .build();

        createEventTypesPort.createEventTypeVersion(version, eventTypeID);
    }

    private JsonSchema createJsonSchema(String schema) {
        return eventTypeVersionSchemaService.convertToJsonSchema(schema);
    }
}
