package com.govstack.information_mediator.pubsub.management.domain.usecase.publisher;


import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.PublisherNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchPublishersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.TerminatePublisherPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.publisher.TerminatePublisherUseCase.Request;
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
class TerminatePublisherServiceTest {

    @Mock
    private FetchManagersPort fetchManagersPort;
    @Mock
    private FetchRoomsPort fetchRoomsPort;
    @Mock
    private FetchPublishersPort fetchPublishersPort;
    @Mock
    private TerminatePublisherPort terminatePublisherPort;
    @InjectMocks
    private TerminatePublisherService terminatePublisherService;

    @Test
    void shouldTerminatePublisher() {
        // When
        var manager = new ManagerIdentifier("EE/BUSINESS/123456789");
        var room = new RoomIdentifier("PatientPortal");
        var publisher = new PublisherID(UUIDGenerator.randomUUID());
        var request = new Request(manager, room, publisher);

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        var roomID = new RoomID(UUIDGenerator.randomUUID());

        when(fetchManagersPort.fetchManagerID(manager)).thenReturn(Optional.of(managerID));
        when(fetchRoomsPort.fetchRoomID(room, managerID)).thenReturn(Optional.of(roomID));
        when(fetchPublishersPort.isPublisherInRoom(publisher, roomID)).thenReturn(true);

        // Then
        Assertions.assertAll(
                () -> assertThatNoException().isThrownBy(() -> terminatePublisherService.execute(request)),
                () -> verify(terminatePublisherPort).terminate(publisher)
        );
    }

    @Test
    void shouldThrowExceptionWhenPublisherIsNotFound() {
        // When
        var manager = new ManagerIdentifier("EE/BUSINESS/123456789");
        var room = new RoomIdentifier("PatientPortal");
        var publisher = new PublisherID(UUIDGenerator.randomUUID());
        var request = new Request(manager, room, publisher);

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        var roomID = new RoomID(UUIDGenerator.randomUUID());

        when(fetchManagersPort.fetchManagerID(manager)).thenReturn(Optional.of(managerID));
        when(fetchRoomsPort.fetchRoomID(room, managerID)).thenReturn(Optional.of(roomID));
        when(fetchPublishersPort.isPublisherInRoom(publisher, roomID)).thenReturn(false);

        // Then
        Assertions.assertAll(
                () -> assertThatThrownBy(() -> terminatePublisherService.execute(request)).isInstanceOf(PublisherNotFoundException.class),
                () -> verifyNoInteractions(terminatePublisherPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // When
        var manager = new ManagerIdentifier("EE/BUSINESS/123456789");
        var room = new RoomIdentifier("PatientPortal");
        var publisher = new PublisherID(UUIDGenerator.randomUUID());
        var request = new Request(manager, room, publisher);

        var managerID = new ManagerID(UUIDGenerator.randomUUID());

        when(fetchManagersPort.fetchManagerID(manager)).thenReturn(Optional.of(managerID));
        when(fetchRoomsPort.fetchRoomID(room, managerID)).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
                () -> assertThatThrownBy(() -> terminatePublisherService.execute(request)).isInstanceOf(RoomNotFoundException.class),
                () -> verifyNoInteractions(terminatePublisherPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // When
        var manager = new ManagerIdentifier("EE/BUSINESS/123456789");
        var room = new RoomIdentifier("PatientPortal");
        var publisher = new PublisherID(UUIDGenerator.randomUUID());
        var request = new Request(manager, room, publisher);

        when(fetchManagersPort.fetchManagerID(manager)).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
                () -> assertThatThrownBy(() -> terminatePublisherService.execute(request)).isInstanceOf(ManagerNotFoundException.class),
                () -> verifyNoInteractions(terminatePublisherPort)
        );
    }
}
