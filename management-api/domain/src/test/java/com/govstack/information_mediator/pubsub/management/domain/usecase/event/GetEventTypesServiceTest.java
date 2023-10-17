package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.management.domain.usecase.event.GetEventTypesUseCase.Request;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetEventTypesServiceTest {

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @Mock
    private FetchManagersPort fetchManagersPort;

    @Mock
    private FetchEventTypesPort fetchEventTypesPort;

    @InjectMocks
    private GetEventTypesService getEventTypesService;

    @Test
    void shouldRetrieveEventTypes() {
        // Given
        var request = defaultRequest();
        var managerID = new ManagerID(UUID.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.manager())).thenReturn(Optional.of(managerID));

        var roomID = new RoomID(UUID.randomUUID());
        when(fetchRoomsPort.fetchRoomID(request.room(), managerID)).thenReturn(Optional.of(roomID));

        var eventTypeId1 = UUIDGenerator.randomUUID();
        var eventTypeId2 = UUIDGenerator.randomUUID();

        var paged = Page.<EventTypeView>builder()
            .content(List.of(
                eventTypeSummaryWith(eventTypeId1, "newPatient"),
                eventTypeSummaryWith(eventTypeId2, "broadcast")
            ))
            .build();

        when(fetchEventTypesPort.fetchEventTypeViews(roomID, request.pageRequest())).thenReturn(paged);

        // Then
        Assertions.assertAll(
            () -> {
                var eventTypes = getEventTypesService.execute(request).eventTypes().getContent();
                assertThat(eventTypes, hasSize(2));

                assertThat(eventTypes.get(0).getId(), equalTo(eventTypeId1));
                assertThat(eventTypes.get(0).getIdentifier(), equalTo("newPatient"));

                assertThat(eventTypes.get(1).getId(), equalTo(eventTypeId2));
                assertThat(eventTypes.get(1).getIdentifier(), equalTo("broadcast"));
            }
        );
    }

    @Test
    void shouldReturnEmptyListWhenRoomHasZeroEvents() {
        // Given
        var request = defaultRequest();
        var managerID = new ManagerID(UUID.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.manager())).thenReturn(Optional.of(managerID));

        var roomID = new RoomID(UUID.randomUUID());
        when(fetchRoomsPort.fetchRoomID(request.room(), managerID)).thenReturn(Optional.of(roomID));

        var paged = Page.<EventTypeView>builder().content(Collections.emptyList()).build();
        when(fetchEventTypesPort.fetchEventTypeViews(roomID, request.pageRequest())).thenReturn(paged);

        // Then
        Assertions.assertAll(
            () -> {
                var eventTypes = getEventTypesService.execute(request).eventTypes().getContent();
                assertThat(eventTypes, hasSize(0));
            }
        );
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // Given
        var request = defaultRequest();
        var managerID = new ManagerID(UUID.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.manager())).thenReturn(Optional.of(managerID));

        when(fetchRoomsPort.fetchRoomID(request.room(), managerID)).thenReturn(Optional.empty());

        // When
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> getEventTypesService.execute(request))
                .isInstanceOf(RoomNotFoundException.class)
                .hasMessage("Room was not found"),

            () -> verifyNoInteractions(fetchEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // Given
        var request = defaultRequest();
        when(fetchManagersPort.fetchManagerID(request.manager())).thenReturn(Optional.empty());

        // When
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> getEventTypesService.execute(request))
                .isInstanceOf(ManagerNotFoundException.class)
                .hasMessage("Manager was not found"),

            () -> verifyNoInteractions(fetchEventTypesPort, fetchRoomsPort)
        );
    }

    private static Request defaultRequest() {
        return new Request(
            new ManagerIdentifier("EE/BUSINESS/123456789"),
            new RoomIdentifier("PatientPortal"),
            PageRequest.builder().build());
    }

    private static EventTypeView eventTypeSummaryWith(UUID id, String identifier) {
        return EventTypeView.builder().id(id).identifier(identifier).build();
    }
}
