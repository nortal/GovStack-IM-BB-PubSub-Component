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
class DeleteEventTypeServiceTest {

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @Mock
    private FetchManagersPort fetchManagersPort;

    @Mock
    private FetchEventTypesPort fetchEventTypesPort;

    @Mock
    private DeleteEventTypesPort deleteEventTypesPort;

    @InjectMocks
    private DeleteEventTypeService deleteEventTypeService;

    @Test
    void shouldDeleteEventType() {
        // Given
        var manager = Manager.builder().build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(manager));

        var room = Room.builder().build();
        when(fetchRoomsPort.fetchRoom("PatientPortal", manager)).thenReturn(Optional.of(room));

        var eventType = EventType.builder().build();
        when(fetchEventTypesPort.fetchEventType("newPatient", room)).thenReturn(Optional.of(eventType));

        // When
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> deleteEventTypeService.execute(defaultRequest())),
            () -> verify(deleteEventTypesPort).deleteEventType(eventType)
        );
    }

    @Test
    void shouldThrowExceptionWhenEventTypeIsAlreadyDeleted() {
        // Given
        var manager = Manager.builder().build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(manager));

        var room = Room.builder().build();
        when(fetchRoomsPort.fetchRoom("PatientPortal", manager)).thenReturn(Optional.of(room));

        when(fetchEventTypesPort.fetchEventType("newPatient", room)).thenReturn(Optional.empty());

        // When
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> deleteEventTypeService.execute(defaultRequest()))
                .isInstanceOf(EventTypeNotFoundException.class)
                .hasMessage("Event Type was not found"),

            () -> verifyNoInteractions(deleteEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // Given
        var manager = Manager.builder().build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(manager));

        when(fetchRoomsPort.fetchRoom("PatientPortal", manager)).thenReturn(Optional.empty());

        // When
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> deleteEventTypeService.execute(defaultRequest()))
                .isInstanceOf(RoomNotFoundException.class)
                .hasMessage("Room was not found"),

            () -> verifyNoInteractions(fetchEventTypesPort, deleteEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // Given
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.empty());

        // When
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> deleteEventTypeService.execute(defaultRequest()))
                .isInstanceOf(ManagerNotFoundException.class)
                .hasMessage("Manager was not found"),

            () -> verifyNoInteractions(fetchRoomsPort, fetchEventTypesPort, deleteEventTypesPort)
        );
    }

    private static DeleteEventTypesUseCase.Request defaultRequest() {
        return DeleteEventTypesUseCase.Request.builder()
            .manager(DeleteEventTypesUseCase.Request.ManagerData.of("EE/BUSINESS/123456789"))
            .room(DeleteEventTypesUseCase.Request.RoomData.of("PatientPortal"))
            .eventType(DeleteEventTypesUseCase.Request.EventTypeData.of("newPatient"))
            .build();
    }
}
