package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.exception.DuplicateResourceException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import com.govstack.information_mediator.pubsub.domain.entity.EventType.Version;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request.EventTypeData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request.EventTypeData.VersionData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request.ManagerData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request.RoomData;
import com.networknt.schema.JsonSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.EVENT_TYPE_ALREADY_EXISTS;

@Component
@RequiredArgsConstructor
class CreateEventTypesService implements CreateEventTypesUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;
    private final FetchEventTypesPort fetchEventTypesPort;
    private final CreateEventTypesPort createEventTypesPort;
    private final EventTypeVersionSchemaService eventTypeVersionSchemaService;

    @Override
    public void execute(Request request) {
        var manager = fetchManager(request.getManager());
        var room = fetchRoom(request.getRoom(), manager);
        createEventType(request.getEventType(), room);
    }

    private Manager fetchManager(ManagerData managerData) {
        return fetchManagersPort.fetchManager(managerData.getIdentifier()).orElseThrow(ManagerNotFoundException::new);
    }

    private Room fetchRoom(RoomData roomData, Manager manager) {
        return fetchRoomsPort.fetchRoom(roomData.getIdentifier(), manager).orElseThrow(RoomNotFoundException::new);
    }

    private void createEventType(EventTypeData eventTypeData, Room room) {
        var eventTypeExists = fetchEventTypesPort.fetchEventType(eventTypeData.getIdentifier(), room).isPresent();
        if (eventTypeExists) {
            throw new DuplicateResourceException(EVENT_TYPE_ALREADY_EXISTS);
        }
        createEventTypesPort.createEventType(mapToEventType(eventTypeData, room));
    }

    private EventType mapToEventType(EventTypeData eventTypeData, Room room) {
        return EventType.builder()
            .roomId(room.getId())
            .identifier(eventTypeData.getIdentifier())
            .version(mapToVersion(eventTypeData.getVersion()))
            .build();
    }

    private Version mapToVersion(VersionData versionData) {
        return Version.builder()
            .versionNo(versionData.getVersionNo())
            .jsonSchema(createJsonSchema(versionData.getJsonSchema()))
            .build();
    }

    private JsonSchema createJsonSchema(String schema) {
       return eventTypeVersionSchemaService.convertToJsonSchema(schema);
    }
}
