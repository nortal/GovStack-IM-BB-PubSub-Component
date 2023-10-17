package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.management.domain.port.DeleteEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.DeleteEventTypesUseCase.Request.EventTypeData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.DeleteEventTypesUseCase.Request.ManagerData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.DeleteEventTypesUseCase.Request.RoomData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DeleteEventTypeService implements DeleteEventTypesUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;
    private final FetchEventTypesPort fetchEventTypesPort;
    private final DeleteEventTypesPort deleteEventTypesPort;

    @Override
    public void execute(Request request) {
        var manager = fetchManager(request.getManager());
        var room = fetchRoom(request.getRoom(), manager);
        var eventType = fetchEventType(request.getEventType(), room);
        deleteEventType(eventType);
    }

    private Manager fetchManager(ManagerData managerData) {
        return fetchManagersPort.fetchManager(managerData.getIdentifier()).orElseThrow(ManagerNotFoundException::new);
    }

    private Room fetchRoom(RoomData roomData, Manager manager) {
        return fetchRoomsPort.fetchRoom(roomData.getIdentifier(), manager).orElseThrow(RoomNotFoundException::new);
    }

    private EventType fetchEventType(EventTypeData eventTypeData, Room room) {
        return fetchEventTypesPort.fetchEventType(eventTypeData.getIdentifier(), room)
            .orElseThrow(EventTypeNotFoundException::new);
    }

    private void deleteEventType(EventType eventType) {
        deleteEventTypesPort.deleteEventType(eventType);
    }
}
