package com.govstack.information_mediator.pubsub.management.web.room;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.view.RoomView;
import com.govstack.information_mediator.pubsub.management.domain.usecase.room.*;
import com.govstack.information_mediator.pubsub.management.domain.view.RoomDetailedView;
import com.govstack.information_mediator.pubsub.management.web.paging.PagingSortingParametersDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class RoomsControllerTest {

    @Mock
    private CreateRoomUseCase createRoomUseCase;

    @Mock
    private GetRoomDetailsUseCase getRoomDetailsUseCase;

    @Mock
    private ListRoomsUseCase listRoomsUseCase;

    @Mock
    private ModifyRoomUseCase modifyRoomUseCase;

    @Mock
    private TerminateRoomsUseCase terminateRoomsUseCase;

    @InjectMocks
    private RoomsController roomsController;

    @Test
    void testCreateRoomUseCaseIsCalledCorrectly() {
        // Given
        var createRoomDTO = CreateRoomDTO.builder()
            .identifier("emergencyNotifications")
            .managerIdentifier("EE/BUSINESS/123456789")
            .messageExpiration(1000000)
            .deliveryDelay(1)
            .deliveryDelayMultiplier(1.01)
            .deliveryAttempts(10)
            .build();

        var roomId = UUID.randomUUID();
        var createRoomResponse = CreateRoomUseCase.Response.of(roomId);
        when(createRoomUseCase.execute(any(CreateRoomUseCase.Request.class))).thenReturn(createRoomResponse);

        // When
        Assertions.assertAll(
            () -> {
                var response = roomsController.createRoom(createRoomDTO);
                assertThat(response.getBody().getRoomId(), equalTo(roomId));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(CreateRoomUseCase.Request.class);
                verify(createRoomUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.getRoom().getIdentifier(), equalTo("emergencyNotifications"));
                assertThat(request.getManager().getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(request.getRoom().getConfiguration().getMessageExpiration(), equalTo(1000000));
                assertThat(request.getRoom().getConfiguration().getDeliveryDelay(), equalTo(1));
                assertThat(request.getRoom().getConfiguration().getDeliveryDelayMultiplier(), equalTo(1.01));
                assertThat(request.getRoom().getConfiguration().getDeliveryAttempts(), equalTo(10));
            }
        );
    }

    @Test
    void testGetRoomDetails() {
        // Given
        var managerIdentifier = "EE/BUSINESS/123456789";
        var roomIdentifier = "emergencyNotifications";
        var roomId = UUID.randomUUID();

        var configuration = RoomDetailedView.Configuration.builder()
            .messageExpiration(1000000)
            .deliveryDelay(1)
            .deliveryDelayMultiplier(1.01)
            .deliveryAttempts(10)
            .build();

        var roomView = RoomDetailedView.builder()
            .id(roomId)
            .identifier(roomIdentifier)
            .managerIdentifier(managerIdentifier)
            .configuration(configuration)
            .createdAt(Instant.now())
            .createdBy("testUser")
            .build();

        var manager = Manager.builder().id(UUID.randomUUID()).identifier(managerIdentifier).build();  // Assuming Manager has an identifier field.
        var response = GetRoomDetailsUseCase.Response.of(roomView, manager);

        when(getRoomDetailsUseCase.execute(any(GetRoomDetailsUseCase.Request.class))).thenReturn(response);

        // When
        ResponseEntity<RoomFullResponseDTO> result = roomsController.getRoomDetailsViaXRoad(managerIdentifier, roomIdentifier);

        // Then
        assertThat(result.getStatusCode(), equalTo(OK));
        assertThat(Objects.requireNonNull(result.getBody()).getIdentifier(), equalTo(roomIdentifier));
        assertThat(result.getBody().getManagerIdentifier(), equalTo(managerIdentifier));
        assertThat(result.getBody().getMessageExpiration(), equalTo(configuration.getMessageExpiration()));
        assertThat(result.getBody().getDeliveryDelay(), equalTo(configuration.getDeliveryDelay()));
        assertThat(result.getBody().getDeliveryDelayMultiplier(), equalTo(configuration.getDeliveryDelayMultiplier()));
        assertThat(result.getBody().getDeliveryAttempts(), equalTo(configuration.getDeliveryAttempts()));
        assertThat(result.getBody().getCreatedAt(), equalTo(roomView.getCreatedAt()));
        assertThat(result.getBody().getCreatedBy(), equalTo(roomView.getCreatedBy()));
    }


    @Test
    void testListAllRooms() {
        // Given
        var room1 = RoomView.builder().id(UUID.randomUUID()).identifier("room1").managerIdentifier("manager-001").build();
        var room2 = RoomView.builder().id(UUID.randomUUID()).identifier("room2").managerIdentifier("manager-002").build();
        var roomsPage = Page.<RoomView>builder()
            .content(List.of(room1, room2))
            .maxItemsPerPage(10)
            .currentPageNumber(1)
            .currentPageNumberOfElements(2)
            .totalNumberOfElements(2)
            .build();
        when(listRoomsUseCase.execute(any(ListRoomsUseCase.Request.class)))
            .thenReturn(new ListRoomsUseCase.Response(roomsPage));

        var pagingSortingParameters = PagingSortingParametersDTO.builder().limit(10).build();

        // When
        var response = roomsController.listRoomsViaXRoad(null, pagingSortingParameters);

        // Then
        assertNotNull(response.getBody());
        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody().getRooms().size(), equalTo(2));
        assertThat(response.getBody().getRooms().get(0).getIdentifier(), equalTo("room1"));
        assertThat(response.getBody().getRooms().get(0).getManagerIdentifier(), equalTo("manager-001"));
        assertThat(response.getBody().getRooms().get(1).getIdentifier(), equalTo("room2"));
        assertThat(response.getBody().getRooms().get(1).getManagerIdentifier(), equalTo("manager-002"));
    }


    @Test
    void testListRoomsForSpecificManager() {
        // Given
        var managerIdentifier = "manager-001";
        var room1 = RoomView.builder().id(UUID.randomUUID()).identifier("room1").managerIdentifier(managerIdentifier).build();
        var roomsPage = Page.<RoomView>builder()
            .content(List.of(room1))
            .maxItemsPerPage(10)
            .currentPageNumber(1)
            .currentPageNumberOfElements(1)
            .totalNumberOfElements(1)
            .build();

        when(listRoomsUseCase.execute(any(ListRoomsUseCase.Request.class)))
            .thenReturn(new ListRoomsUseCase.Response(roomsPage));

        var pagingSortingParameters = PagingSortingParametersDTO.builder().limit(10).build();

        // When
        var response = roomsController.listRoomsViaXRoad(managerIdentifier, pagingSortingParameters);

        // Then
        assertNotNull(response.getBody());
        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody().getRooms().size(), equalTo(1));
        assertThat(response.getBody().getRooms().get(0).getIdentifier(), equalTo("room1"));
        assertThat(response.getBody().getRooms().get(0).getManagerIdentifier(), equalTo(managerIdentifier));

        var captor = ArgumentCaptor.forClass(ListRoomsUseCase.Request.class);
        verify(listRoomsUseCase).execute(captor.capture());
        assertThat(captor.getValue().getMemberIdentifier(), equalTo(managerIdentifier));
    }

    @Test
    void testModifyRoomUseCaseIsCalledCorrectly() {
        // Given
        var managerIdentifier = "EE/BUSINESS/123456789";
        var roomIdentifier = "emergencyNotifications";
        var newRoomIdentifier = "newEmergencyNotifications";
        var modifyRoomDTO = ModifyRoomDTO.builder()
            .identifier(newRoomIdentifier)
            .build();

        var roomId = UUID.randomUUID();
        var manager = Manager.builder().id(UUID.randomUUID()).build();
        var modifiedRoom = Room.builder()
            .id(roomId)
            .identifier(newRoomIdentifier)
            .configuration(
                Room.Configuration.builder().build()
            ).build();
        var modifyRoomResponse = ModifyRoomUseCase.Response.of(modifiedRoom, manager);
        when(modifyRoomUseCase.execute(any(ModifyRoomUseCase.Request.class))).thenReturn(modifyRoomResponse);

        // When
        Assertions.assertAll(
            () -> {
                var response = roomsController.modifyRoomAsAdministrator(managerIdentifier, roomIdentifier, modifyRoomDTO);
                assertThat(response.getBody().getIdentifier(), equalTo(newRoomIdentifier));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(ModifyRoomUseCase.Request.class);
                verify(modifyRoomUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.getRoom().getIdentifier(), equalTo(roomIdentifier));
                assertThat(request.getManager().getIdentifier(), equalTo(managerIdentifier));
                assertThat(request.getNewRoomData().getIdentifier(), equalTo(newRoomIdentifier));
            }
        );
    }

    @Test
    void testTerminateRoomsUseCaseIsCalled() {
        Assertions.assertAll(
            () -> {
                var response = roomsController.terminateRoomViaXRoad("manager-001", "room-001");
                assertThat(response.getStatusCode(), equalTo(NO_CONTENT));
            },
            () -> {
                var argument = ArgumentCaptor.forClass(TerminateRoomsUseCase.Request.class);
                verify(terminateRoomsUseCase).execute(argument.capture());

                assertThat(argument.getValue().manager().identifier(), equalTo("manager-001"));
                assertThat(argument.getValue().room().identifier(), equalTo("room-001"));
            }
        );
    }
}
