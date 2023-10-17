package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.exception.BusinessViolationException;
import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.ResourceNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
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
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.DeleteEventTypeVersionsUseCase.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteEventTypeVersionsServiceTest {

    private final RoomIdentifier roomIdentifier = new RoomIdentifier("PatientPortal");
    private final ManagerIdentifier managerIdentifier = new ManagerIdentifier("EE/BUSINESS/123456789");
    private final EventTypeIdentifier eventTypeIdentifier = new EventTypeIdentifier("newPatient");

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @Mock
    private FetchManagersPort fetchManagersPort;

    @Mock
    private FetchEventTypesPort fetchEventTypesPort;

    @Mock
    private DeleteEventTypesPort deleteEventTypesPort;

    @InjectMocks
    private DeleteEventTypeVersionsService deleteEventTypeVersionsService;

    @Test
    void shouldDeleteEventTypeVersion() {
        // Given
        var request = defaultRequest();

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.of(managerID));

        var roomID = new RoomID(UUIDGenerator.randomUUID());
        when(fetchRoomsPort.fetchRoomID(request.getRoomIdentifier(), managerID)).thenReturn(Optional.of(roomID));

        var eventTypeID = new EventTypeID(UUIDGenerator.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeID(request.getEventTypeIdentifier(), roomID)).thenReturn(Optional.of(eventTypeID));

        var eventTypeVersionID = new EventTypeVersionID(UUIDGenerator.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeVersionID(eventTypeID, 12)).thenReturn(Optional.of(eventTypeVersionID));

        when(fetchEventTypesPort.countEventTypeVersions(eventTypeID)).thenReturn(2);

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> deleteEventTypeVersionsService.execute(request)),
            () -> verify(deleteEventTypesPort).deleteEventTypeVersion(eventTypeVersionID)
        );
    }

    @Test
    void shouldThrowExceptionWhenEventTypeHasOnlyOneVersion() {
        // Given
        var request = defaultRequest();

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.of(managerID));

        var roomID = new RoomID(UUIDGenerator.randomUUID());
        when(fetchRoomsPort.fetchRoomID(request.getRoomIdentifier(), managerID)).thenReturn(Optional.of(roomID));

        var eventTypeID = new EventTypeID(UUIDGenerator.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeID(request.getEventTypeIdentifier(), roomID)).thenReturn(Optional.of(eventTypeID));

        var eventTypeVersionID = new EventTypeVersionID(UUIDGenerator.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeVersionID(eventTypeID, 12)).thenReturn(Optional.of(eventTypeVersionID));

        when(fetchEventTypesPort.countEventTypeVersions(eventTypeID)).thenReturn(1);

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> deleteEventTypeVersionsService.execute(request))
                .isInstanceOf(BusinessViolationException.class)
                .hasMessage("Deletion of event type version is not allowed"),

            () -> verifyNoInteractions(deleteEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenEventTypeVersionIsNotFound() {
        // Given
        var request = defaultRequest();

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.of(managerID));

        var roomID = new RoomID(UUIDGenerator.randomUUID());
        when(fetchRoomsPort.fetchRoomID(request.getRoomIdentifier(), managerID)).thenReturn(Optional.of(roomID));

        var eventTypeID = new EventTypeID(UUIDGenerator.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeID(request.getEventTypeIdentifier(), roomID)).thenReturn(Optional.of(eventTypeID));

        when(fetchEventTypesPort.fetchEventTypeVersionID(eventTypeID, 12)).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> deleteEventTypeVersionsService.execute(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Event Type Version was not found"),

            () -> verifyNoInteractions(deleteEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenEventTypeIsNotFound() {
        // Given
        var request = defaultRequest();

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.of(managerID));

        var roomID = new RoomID(UUIDGenerator.randomUUID());
        when(fetchRoomsPort.fetchRoomID(request.getRoomIdentifier(), managerID)).thenReturn(Optional.of(roomID));

        when(fetchEventTypesPort.fetchEventTypeID(request.getEventTypeIdentifier(), roomID)).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> deleteEventTypeVersionsService.execute(request))
                .isInstanceOf(EventTypeNotFoundException.class)
                .hasMessage("Event Type was not found"),

            () -> verifyNoInteractions(deleteEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // Given
        var request = defaultRequest();

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.of(managerID));

        when(fetchRoomsPort.fetchRoomID(request.getRoomIdentifier(), managerID)).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> deleteEventTypeVersionsService.execute(request))
                .isInstanceOf(RoomNotFoundException.class)
                .hasMessage("Room was not found"),

            () -> verifyNoInteractions(deleteEventTypesPort, fetchEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // Given
        var request = defaultRequest();

        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> deleteEventTypeVersionsService.execute(request))
                .isInstanceOf(ManagerNotFoundException.class)
                .hasMessage("Manager was not found"),

            () -> verifyNoInteractions(deleteEventTypesPort, fetchEventTypesPort, fetchRoomsPort)
        );
    }

    private Request defaultRequest() {
        return Request.builder()
            .roomIdentifier(roomIdentifier)
            .managerIdentifier(managerIdentifier)
            .eventTypeIdentifier(eventTypeIdentifier)
            .versionNo(12)
            .build();
    }
}
