package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.view.RoomView;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.view.RoomDetailedView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(MockitoExtension.class)
class GetRoomDetailsServiceTest {

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @Mock
    private FetchManagersPort fetchManagersPort;

    @InjectMocks
    private GetRoomDetailsService getRoomDetailsService;

    @Test
    void shouldFetchRoomDetailsWhenBothManagerAndRoomExist() {
        // Given
        var managerIdentifier = "manager-001";
        var roomIdentifier = "room-001";

        var request = GetRoomDetailsUseCase.Request.builder()
            .manager(GetRoomDetailsUseCase.Request.ManagerData.of(managerIdentifier))
            .room(GetRoomDetailsUseCase.Request.RoomData.of(roomIdentifier))
            .build();

        var manager = Manager.builder().id(UUID.randomUUID()).build();
        var roomDetailedView = RoomDetailedView.builder()
            .identifier(roomIdentifier)
            .managerIdentifier(managerIdentifier)
            .build();

        when(fetchManagersPort.fetchManager(managerIdentifier)).thenReturn(Optional.of(manager));
        when(fetchRoomsPort.fetchRoomDetailedView(roomIdentifier, manager)).thenReturn(Optional.of(roomDetailedView));

        // When
        var response = getRoomDetailsService.execute(request);

        // Then
        Assertions.assertEquals(roomDetailedView, response.getRoom());
        Assertions.assertEquals(manager, response.getManager());
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // Given
        var managerIdentifier = "manager-001";
        var request = GetRoomDetailsUseCase.Request.builder()
            .manager(GetRoomDetailsUseCase.Request.ManagerData.of(managerIdentifier))
            .room(GetRoomDetailsUseCase.Request.RoomData.of("room-001"))
            .build();

        when(fetchManagersPort.fetchManager(managerIdentifier)).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(ManagerNotFoundException.class)
            .isThrownBy(() -> getRoomDetailsService.execute(request));
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // Given
        var managerIdentifier = "manager-001";
        var roomIdentifier = "room-001";

        var request = GetRoomDetailsUseCase.Request.builder()
            .manager(GetRoomDetailsUseCase.Request.ManagerData.of(managerIdentifier))
            .room(GetRoomDetailsUseCase.Request.RoomData.of(roomIdentifier))
            .build();

        var manager = Manager.builder().id(UUID.randomUUID()).build();

        when(fetchManagersPort.fetchManager(managerIdentifier)).thenReturn(Optional.of(manager));
        when(fetchRoomsPort.fetchRoomDetailedView(roomIdentifier, manager)).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(RoomNotFoundException.class)
            .isThrownBy(() -> getRoomDetailsService.execute(request));
    }
}


