package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.GetEventTypeVersionsUseCase.Request;
import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeVersionOverview;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetEventTypeVersionsServiceTest {

    private final RoomIdentifier roomIdentifier = new RoomIdentifier("PatientPortal");
    private final ManagerIdentifier managerIdentifier = new ManagerIdentifier("EE/BUSINESS/123456789");
    private final EventTypeIdentifier eventTypeIdentifier = new EventTypeIdentifier("newPatient");

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @Mock
    private FetchManagersPort fetchManagersPort;

    @Mock
    private FetchEventTypesPort fetchEventTypesPort;

    @InjectMocks
    private GetEventTypeVersionsService getEventTypeVersionsService;

    @Test
    void shouldReturnEventVersionTypes() {
        var request = defaultRequest();

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.of(managerID));

        var roomID = new RoomID(UUIDGenerator.randomUUID());
        when(fetchRoomsPort.fetchRoomID(request.getRoomIdentifier(), managerID)).thenReturn(Optional.of(roomID));

        var eventTypeID = new EventTypeID(UUIDGenerator.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeID(request.getEventTypeIdentifier(), roomID)).thenReturn(Optional.of(eventTypeID));

        var firstEvenTypeVersion = EventTypeVersionOverview.builder().build();
        var secondEvenTypeVersion = EventTypeVersionOverview.builder().build();
        when(fetchEventTypesPort.fetchEventTypeVersionOverviews(eventTypeID)).thenReturn(List.of(firstEvenTypeVersion, secondEvenTypeVersion));

        // Then
        Assertions.assertAll(
            () -> {
                var response = getEventTypeVersionsService.execute(defaultRequest());
                assertThat(response.eventTypeVersions(), hasSize(2));
                assertThat(response.eventTypeVersions(), contains(firstEvenTypeVersion, secondEvenTypeVersion));
            }
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
        assertThatThrownBy(() -> getEventTypeVersionsService.execute(defaultRequest()))
            .isInstanceOf(EventTypeNotFoundException.class)
            .hasMessage("Event Type was not found");
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // Given
        var request = defaultRequest();

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.of(managerID));

        when(fetchRoomsPort.fetchRoomID(request.getRoomIdentifier(), managerID)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> getEventTypeVersionsService.execute(defaultRequest()))
            .isInstanceOf(RoomNotFoundException.class)
            .hasMessage("Room was not found");
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // Given
        var request = defaultRequest();

        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> getEventTypeVersionsService.execute(request))
            .isInstanceOf(ManagerNotFoundException.class)
            .hasMessage("Manager was not found");
    }

    private Request defaultRequest() {
        return Request.builder()
            .roomIdentifier(roomIdentifier)
            .managerIdentifier(managerIdentifier)
            .eventTypeIdentifier(eventTypeIdentifier)
            .build();
    }
}
