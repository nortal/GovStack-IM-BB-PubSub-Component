package com.govstack.information_mediator.pubsub.management.domain.usecase.publisher;

import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.PublisherNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchPublishersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.PublisherConstraintsPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdatePublisherServiceTest {

    @Mock
    private FetchManagersPort fetchManagersPort;
    @Mock
    private FetchRoomsPort fetchRoomsPort;
    @Mock
    private FetchPublishersPort fetchPublishersPort;
    @Mock
    private FetchEventTypesPort fetchEventTypesPort;
    @Mock
    private PublisherConstraintsPort publisherConstraintsPort;

    @InjectMocks
    private UpdatePublisherService updatePublisherService;

    @Test
    void shouldUpdatePublisher() {
        // Given
        var manager = new ManagerIdentifier("EE/BUSINESS/123456789");
        var room = new RoomIdentifier("PatientPortal");

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        var roomID = new RoomID(UUIDGenerator.randomUUID());
        var publisher = new PublisherID(UUIDGenerator.randomUUID());

        var eventTypes = List.of("newPatient", "delivery");
        var eventTypeID1 = new EventTypeID(UUIDGenerator.randomUUID());
        var eventTypeID2 = new EventTypeID(UUIDGenerator.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeID(any(EventTypeIdentifier.class), eq(roomID)))
                .thenReturn(Optional.of(eventTypeID1), Optional.of(eventTypeID2));

        var eventTypeID3 = UUIDGenerator.randomUUID();
        var eventTypeID4 = UUIDGenerator.randomUUID();
        var constraints = List.of(eventTypeID1.getId(), eventTypeID3, eventTypeID4);
        when(publisherConstraintsPort.getPublisherEventTypeIds(publisher)).thenReturn(constraints);

        when(fetchManagersPort.fetchManagerID(manager)).thenReturn(Optional.of(managerID));
        when(fetchRoomsPort.fetchRoomID(room, managerID)).thenReturn(Optional.of(roomID));
        when(fetchPublishersPort.isPublisherInRoom(publisher, roomID)).thenReturn(true);

        var request = new UpdatePublisherUseCase.Request(manager, room, publisher, eventTypes);

        // Then
        Assertions.assertAll(
                () -> assertThatNoException().isThrownBy(() -> updatePublisherService.execute(request)),
                () -> {
                    var captor = ArgumentCaptor.forClass(UUID.class);
                    verify(publisherConstraintsPort, times(2)).removeEventTypeConstraint(eq(publisher), captor.capture());
                    assertThat(captor.getAllValues(), contains(eventTypeID3, eventTypeID4));
                },
                () -> {
                    var captor = ArgumentCaptor.forClass(UUID.class);
                    verify(publisherConstraintsPort).addEventTypeConstraint(eq(publisher), captor.capture());
                    assertThat(captor.getValue(), equalTo(eventTypeID2.getId()));
                }
        );
    }

    @Test
    void shouldThrowExceptionWhenEventTypeIsNotFound() {
        // Given
        var manager = new ManagerIdentifier("EE/BUSINESS/123456789");
        var room = new RoomIdentifier("PatientPortal");

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        var roomID = new RoomID(UUIDGenerator.randomUUID());
        var publisher = new PublisherID(UUIDGenerator.randomUUID());

        var eventTypes = List.of("newPatient", "delivery");
        var eventTypeID1 = new EventTypeID(UUIDGenerator.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeID(any(EventTypeIdentifier.class), eq(roomID)))
                .thenReturn(Optional.of(eventTypeID1), Optional.empty());

        when(fetchManagersPort.fetchManagerID(manager)).thenReturn(Optional.of(managerID));
        when(fetchRoomsPort.fetchRoomID(room, managerID)).thenReturn(Optional.of(roomID));
        when(fetchPublishersPort.isPublisherInRoom(publisher, roomID)).thenReturn(true);

        var request = new UpdatePublisherUseCase.Request(manager, room, publisher, eventTypes);

        // Then
        Assertions.assertAll(
                () -> assertThatException()
                        .isThrownBy(() -> updatePublisherService.execute(request))
                        .isInstanceOf(EventTypeNotFoundException.class),
                () -> verifyNoInteractions(publisherConstraintsPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenEventTypeIsEmpty() {
        // Given
        var manager = new ManagerIdentifier("EE/BUSINESS/123456789");
        var room = new RoomIdentifier("PatientPortal");

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        var roomID = new RoomID(UUIDGenerator.randomUUID());
        var publisher = new PublisherID(UUIDGenerator.randomUUID());

        when(fetchManagersPort.fetchManagerID(manager)).thenReturn(Optional.of(managerID));
        when(fetchRoomsPort.fetchRoomID(room, managerID)).thenReturn(Optional.of(roomID));
        when(fetchPublishersPort.isPublisherInRoom(publisher, roomID)).thenReturn(true);

        var request = new UpdatePublisherUseCase.Request(manager, room, publisher, List.of());

        // Then
        Assertions.assertAll(
                () -> assertThatException()
                        .isThrownBy(() -> updatePublisherService.execute(request))
                        .isInstanceOf(ApiException.class)
                        .withMessage("At least one event type is required for update the publisher"),
                () -> verifyNoInteractions(publisherConstraintsPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenPublisherIsNotInRoom() {
        // Given
        var manager = new ManagerIdentifier("EE/BUSINESS/123456789");
        var room = new RoomIdentifier("PatientPortal");

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        var roomID = new RoomID(UUIDGenerator.randomUUID());
        var publisher = new PublisherID(UUIDGenerator.randomUUID());

        when(fetchManagersPort.fetchManagerID(manager)).thenReturn(Optional.of(managerID));
        when(fetchRoomsPort.fetchRoomID(room, managerID)).thenReturn(Optional.of(roomID));
        when(fetchPublishersPort.isPublisherInRoom(publisher, roomID)).thenReturn(false);

        var request = new UpdatePublisherUseCase.Request(manager, room, publisher, List.of());

        // Then
        Assertions.assertAll(
                () -> assertThatException()
                        .isThrownBy(() -> updatePublisherService.execute(request))
                        .isInstanceOf(PublisherNotFoundException.class),
                () -> verifyNoInteractions(publisherConstraintsPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // Given
        var manager = new ManagerIdentifier("EE/BUSINESS/123456789");
        var room = new RoomIdentifier("PatientPortal");

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        var publisher = new PublisherID(UUIDGenerator.randomUUID());

        when(fetchManagersPort.fetchManagerID(manager)).thenReturn(Optional.of(managerID));
        when(fetchRoomsPort.fetchRoomID(room, managerID)).thenReturn(Optional.empty());

        var request = new UpdatePublisherUseCase.Request(manager, room, publisher, List.of());

        // Then
        Assertions.assertAll(
                () -> assertThatException()
                        .isThrownBy(() -> updatePublisherService.execute(request))
                        .isInstanceOf(RoomNotFoundException.class),
                () -> verifyNoInteractions(publisherConstraintsPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // Given
        var manager = new ManagerIdentifier("EE/BUSINESS/123456789");
        var room = new RoomIdentifier("PatientPortal");

        var publisher = new PublisherID(UUIDGenerator.randomUUID());

        when(fetchManagersPort.fetchManagerID(manager)).thenReturn(Optional.empty());

        var request = new UpdatePublisherUseCase.Request(manager, room, publisher, List.of());

        // Then
        Assertions.assertAll(
                () -> assertThatException()
                        .isThrownBy(() -> updatePublisherService.execute(request))
                        .isInstanceOf(ManagerNotFoundException.class),
                () -> verifyNoInteractions(publisherConstraintsPort)
        );
    }
}
