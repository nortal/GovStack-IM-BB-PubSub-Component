package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.ModifyRoomPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModifyRoomServiceTest {

    @Mock
    private FetchManagersPort fetchManagersPort;

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @Mock
    private ModifyRoomPort modifyRoomPort;

    @InjectMocks
    private ModifyRoomService modifyRoomService;

    @Test
    void shouldThrowRoomNotFoundExceptionWhenRoomDoesNotExist() {
        // Given
        var request = ModifyRoomUseCase.Request.builder()
            .room(ModifyRoomUseCase.Request.RoomData.builder().identifier("emergencyNotifications").build())
            .manager(ModifyRoomUseCase.Request.ManagerData.builder().identifier("EE/BUSINESS/123456789").build())
            .newRoomData(Room.builder().identifier("newEmergencyNotifications").build())
            .build();

        // Mock manager
        var managerId = UUID.randomUUID();
        var manager = Manager.builder().id(managerId).build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(manager));

        // Make sure room does not exist
        when(fetchRoomsPort.fetchRoom("emergencyNotifications", manager)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> modifyRoomService.execute(request)).isInstanceOf(RoomNotFoundException.class);
    }

    @Test
    void shouldThrowManagerNotFoundExceptionWhenManagerDoesNotExist() {
        // Given
        var request = ModifyRoomUseCase.Request.builder()
            .room(ModifyRoomUseCase.Request.RoomData.builder().identifier("emergencyNotifications").build())
            .manager(ModifyRoomUseCase.Request.ManagerData.builder().identifier("EE/BUSINESS/123456789").build())
            .newRoomData(Room.builder().identifier("newEmergencyNotifications").build())
            .build();

        // Make sure manager does not exist
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> modifyRoomService.execute(request)).isInstanceOf(ManagerNotFoundException.class);
    }

    @Test
    void shouldModifyRoomIdentifier() {
        // Given
        var request = modifyRoomIdentifierRequest();

        var managerId = UUID.randomUUID();
        var manager = Manager.builder().id(managerId).build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(manager));

        var existingRoom = Room.builder().id(UUID.randomUUID()).identifier("emergencyNotifications").build();
        when(fetchRoomsPort.fetchRoom("emergencyNotifications", manager)).thenReturn(Optional.of(existingRoom));

        var modifiedRoom = Room.builder().id(UUID.randomUUID()).identifier("newEmergencyNotifications").build();
        when(modifyRoomPort.modifyRoom(manager, "emergencyNotifications", existingRoom)).thenReturn(modifiedRoom);

        // Then
        var response = modifyRoomService.execute(request);
        assertThat(response.getRoom(), equalTo(modifiedRoom));
        assertThat(response.getManager(), equalTo(manager));
    }

    @Test
    void shouldModifyRoomManager() {
        // Given
        var request = modifyRoomManagerRequest();

        var managerId = UUID.randomUUID();
        var manager = Manager.builder().id(managerId).build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(manager));

        var existingRoom = Room.builder().id(UUID.randomUUID()).identifier("emergencyNotifications").build();
        when(fetchRoomsPort.fetchRoom("emergencyNotifications", manager)).thenReturn(Optional.of(existingRoom));

        var newManagerId = UUID.randomUUID();
        var newManager = Manager.builder().id(newManagerId).build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/987654321")).thenReturn(Optional.of(newManager));

        var modifiedRoom = Room.builder().id(UUID.randomUUID()).identifier("emergencyNotifications").managerId(newManagerId).build();
        when(modifyRoomPort.modifyRoom(manager, "emergencyNotifications", existingRoom)).thenReturn(modifiedRoom);

        // Then
        var response = modifyRoomService.execute(request);
        assertThat(response.getRoom(), equalTo(modifiedRoom));
        assertThat(response.getManager(), equalTo(newManager));
    }

    @Test
    void shouldModifyRoomConfiguration() {
        // Given
        var request = modifyRoomConfigurationRequest();

        var managerId = UUID.randomUUID();
        var manager = Manager.builder().id(managerId).build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(manager));

        var currentConfiguration = Room.Configuration.builder()
            .messageExpiration(10)
            .deliveryDelay(10)
            .deliveryDelayMultiplier(1.0)
            .deliveryAttempts(3)
            .build();
        var room = Room.builder().id(UUID.randomUUID()).identifier("emergencyNotifications").configuration(currentConfiguration).build();
        when(fetchRoomsPort.fetchRoom("emergencyNotifications", manager)).thenReturn(Optional.of(room));

        var modifiedConfiguration = Room.Configuration.builder()
            .messageExpiration(15)
            .deliveryDelay(10)
            .deliveryDelayMultiplier(1.0)
            .deliveryAttempts(3)
            .build();
        var modifiedRoom = Room.builder().id(UUID.randomUUID()).identifier("emergencyNotifications").configuration(modifiedConfiguration).build();
        when(modifyRoomPort.modifyRoom(manager, "emergencyNotifications", room)).thenReturn(modifiedRoom);

        // Then
        var response = modifyRoomService.execute(request);
        assertThat(response.getRoom().getConfiguration(), equalTo(modifiedConfiguration));
        assertThat(response.getManager(), equalTo(manager));
    }

    private static ModifyRoomUseCase.Request modifyRoomIdentifierRequest() {
        return ModifyRoomUseCase.Request.builder()
            .room(ModifyRoomUseCase.Request.RoomData.builder().identifier("emergencyNotifications").build())
            .manager(ModifyRoomUseCase.Request.ManagerData.builder().identifier("EE/BUSINESS/123456789").build())
            .newRoomData(Room.builder().identifier("newEmergencyNotifications").build())
            .build();
    }

    private static ModifyRoomUseCase.Request modifyRoomManagerRequest() {
        return ModifyRoomUseCase.Request.builder()
            .room(ModifyRoomUseCase.Request.RoomData.builder().identifier("emergencyNotifications").build())
            .manager(ModifyRoomUseCase.Request.ManagerData.builder().identifier("EE/BUSINESS/123456789").build())
            .newManagerIdentifier("EE/BUSINESS/987654321")
            .build();
    }

    private static ModifyRoomUseCase.Request modifyRoomConfigurationRequest() {
        var newConfiguration = Room.Configuration.builder()
            .messageExpiration(15)
            .build();
        return ModifyRoomUseCase.Request.builder()
            .room(ModifyRoomUseCase.Request.RoomData.builder().identifier("emergencyNotifications").build())
            .manager(ModifyRoomUseCase.Request.ManagerData.builder().identifier("EE/BUSINESS/123456789").build())
            .newRoomData(Room.builder().configuration(newConfiguration).build())
            .build();
    }
}
