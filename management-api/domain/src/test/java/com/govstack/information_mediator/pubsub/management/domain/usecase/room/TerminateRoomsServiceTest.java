package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.TerminateRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.room.TerminateRoomsUseCase.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TerminateRoomsServiceTest {

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @Mock
    private FetchManagersPort fetchManagersPort;

    @Mock
    private TerminateRoomsPort terminateRoomsPort;

    @InjectMocks
    private TerminateRoomsService terminateRoomsService;

    @Test
    void shouldExecuteRoomTerminationFlow() {
        // Given
        var request = Request.of("manager-001", "room-001");

        var manager = Manager.builder().build();
        when(fetchManagersPort.fetchManager("manager-001")).thenReturn(Optional.of(manager));

        var room = Room.builder().build();
        when(fetchRoomsPort.fetchRoom("room-001", manager)).thenReturn(Optional.of(room));

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> terminateRoomsService.execute(request)),
            () -> verify(terminateRoomsPort).terminate(room)
        );
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // Given
        var request = Request.of("manager-001", "room-001");

        var manager = Manager.builder().build();
        when(fetchManagersPort.fetchManager("manager-001")).thenReturn(Optional.of(manager));

        when(fetchRoomsPort.fetchRoom("room-001", manager)).thenReturn(Optional.empty());

        // Then
        assertThatException()
            .isThrownBy(() -> terminateRoomsService.execute(request))
            .isInstanceOf(RoomNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // Given
        var request = Request.of("manager-001", "room-001");

        var manager = Manager.builder().build();
        when(fetchManagersPort.fetchManager("manager-001")).thenReturn(Optional.empty());

        // Then
        assertThatException()
            .isThrownBy(() -> terminateRoomsService.execute(request))
            .isInstanceOf(ManagerNotFoundException.class);
    }
}
