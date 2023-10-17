package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.view.RoomView;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
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
class ListRoomsServiceTest {

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @Mock
    private FetchManagersPort fetchManagersPort;

    @InjectMocks
    private ListRoomsService listRoomsService;

    @Test
    void shouldFetchAllRoomsWhenMemberIdentifierIsNull() {
        // Given
        var request = ListRoomsUseCase.Request.of(null, PageRequest.builder().build());
        var rooms = Arrays.asList(
            RoomView.builder().identifier("room1").managerIdentifier("manager-001").build(),
            RoomView.builder().identifier("room2").managerIdentifier("manager-002").build()
        );
        var page = Page.<RoomView>builder().content(rooms).build();

        // When
        when(fetchRoomsPort.fetchAllRooms(request.getPageRequest())).thenReturn(page);

        var response = listRoomsService.execute(request);

        // Then
        Assertions.assertEquals(rooms, response.rooms().getContent());
    }

    @Test
    void shouldFetchRoomsByManagerWhenMemberIdentifierIsNotNull() {
        // Given
        var memberIdentifier = "manager-001";
        var request = ListRoomsUseCase.Request.of(memberIdentifier, PageRequest.builder().build());
        var manager = Manager.builder().id(UUID.randomUUID()).build();
        var rooms = Arrays.asList(
            RoomView.builder().identifier("room1").managerIdentifier(memberIdentifier).build(),
            RoomView.builder().identifier("room2").managerIdentifier(memberIdentifier).build()
        );
        var page = Page.<RoomView>builder().content(rooms).build();

        // When
        when(fetchManagersPort.fetchManager(memberIdentifier)).thenReturn(Optional.of(manager));
        when(fetchRoomsPort.fetchRoomsByManager(manager, request.getPageRequest())).thenReturn(page);

        var response = listRoomsService.execute(request);

        // Then
        Assertions.assertEquals(rooms, response.rooms().getContent());
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // Given
        var memberIdentifier = "manager-001";
        var request = ListRoomsUseCase.Request.of(memberIdentifier, PageRequest.builder().build());

        // When
        when(fetchManagersPort.fetchManager(memberIdentifier)).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(ManagerNotFoundException.class)
            .isThrownBy(() -> listRoomsService.execute(request));
    }
}

