package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomAlreadyExistsException;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateRoomPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRoomServiceTest {

    @Mock
    private CreateRoomPort createRoomPort;

    @Mock
    private FetchManagersPort fetchManagersPort;

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @InjectMocks
    private CreateRoomService createRoomService;

    @Test
    void shouldCreateRoom() {
        // Given
        var request = defaultRequest();

        var managerId = UUID.randomUUID();
        var manager = Manager.builder().id(managerId).build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(manager));

        when(fetchRoomsPort.fetchRoom("emergencyNotifications", manager)).thenReturn(Optional.empty());

        var roomId = UUID.randomUUID();
        when(createRoomPort.createRoom(any(Room.class))).thenReturn(roomId);

        // Then
        Assertions.assertAll(
            () -> {
                var response = createRoomService.execute(request);
                assertThat(response.getRoomId(), equalTo(roomId));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(Room.class);
                verify(createRoomPort).createRoom(captor.capture());

                var room = captor.getValue();
                assertThat(room.getManagerId(), equalTo(managerId));
                assertThat(room.getIdentifier(), equalTo("emergencyNotifications"));
                assertThat(room.getConfiguration().getMessageExpiration(), equalTo(1000000));
                assertThat(room.getConfiguration().getDeliveryDelay(), equalTo(1));
                assertThat(room.getConfiguration().getDeliveryDelayMultiplier(), equalTo(1.01));
                assertThat(room.getConfiguration().getDeliveryAttempts(), equalTo(10));
            }
        );
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // Given
        var request = defaultRequest();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> createRoomService.execute(request)).isInstanceOf(ManagerNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenRoomAlreadyExists() {
        // Given
        var request = defaultRequest();

        var managerId = UUID.randomUUID();
        var manager = Manager.builder().id(managerId).build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(manager));

        var existingRoom = Room.builder().id(UUID.randomUUID()).build();
        when(fetchRoomsPort.fetchRoom("emergencyNotifications", manager)).thenReturn(Optional.of(existingRoom));

        // Then
        assertThatThrownBy(() -> createRoomService.execute(request)).isInstanceOf(RoomAlreadyExistsException.class);
    }

    private static CreateRoomUseCase.Request defaultRequest() {
        return CreateRoomUseCase.Request.builder()
            .room(CreateRoomUseCase.Request.RoomData.builder()
                .identifier("emergencyNotifications")
                .configuration(CreateRoomUseCase.Request.ConfigurationData.builder()
                    .messageExpiration(1000000)
                    .deliveryDelay(1)
                    .deliveryDelayMultiplier(1.01)
                    .deliveryAttempts(10)
                    .build())
                .build())
            .manager(CreateRoomUseCase.Request.ManagerData.builder()
                .identifier("EE/BUSINESS/123456789")
                .build())
            .build();
    }
}
