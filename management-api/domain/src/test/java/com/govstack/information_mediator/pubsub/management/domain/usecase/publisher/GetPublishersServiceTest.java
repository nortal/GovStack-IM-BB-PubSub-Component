package com.govstack.information_mediator.pubsub.management.domain.usecase.publisher;

import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.MemberIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.PublisherIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchPublishersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.publisher.GetPublishersUseCase.Request;
import com.govstack.information_mediator.pubsub.management.domain.view.PublisherView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPublishersServiceTest {

    @Mock
    private FetchRoomsPort fetchRoomsPort;
    @Mock
    private FetchManagersPort fetchManagersPort;
    @Mock
    private FetchPublishersPort fetchPublishersPort;

    @InjectMocks
    private GetPublishersService getPublishersService;

    @Test
    void shouldGetPublishersAsManager() {
        // When
        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).managerId(managerID.getId()).build();
        var publishers = List.of(createDummyPublisher(), createDummyPublisher());
        var pageRequest = emptyPageRequest();
        var result = Page.<PublisherView>builder().content(publishers).build();

        when(fetchRoomsPort.fetchRoom("PatientPortal")).thenReturn(Optional.of(room));
        when(fetchManagersPort.fetchManagerID(any(ManagerIdentifier.class))).thenReturn(Optional.of(managerID));
        when(fetchPublishersPort.fetchPublishersView(any(RoomID.class), eq(pageRequest))).thenReturn(result);

        // Then
        Assertions.assertAll(
            () -> {
                var request = new Request(new MemberIdentifier("EE/GOV/123456"), new RoomIdentifier("PatientPortal"), pageRequest);
                var response = getPublishersService.execute(request);
                assertThat(response.publishers().getContent(), hasSize(2));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(RoomID.class);
                verify(fetchPublishersPort).fetchPublishersView(captor.capture(), any(PageRequest.class));
                assertThat(captor.getValue().getId(), equalTo(roomId));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(ManagerIdentifier.class);
                verify(fetchManagersPort).fetchManagerID(captor.capture());
                assertThat(captor.getValue().getIdentifier(), equalTo("EE/GOV/123456"));
            },
            () -> {
                var roomCaptor = ArgumentCaptor.forClass(String.class);
                verify(fetchRoomsPort).fetchRoom(roomCaptor.capture());
                assertThat(roomCaptor.getValue(), equalTo("PatientPortal"));
            },
            () -> {
                var managerCaptor = ArgumentCaptor.forClass(ManagerIdentifier.class);
                verify(fetchManagersPort).fetchManagerID(managerCaptor.capture());
                assertThat(managerCaptor.getValue().getIdentifier(), equalTo("EE/GOV/123456"));
            },
            () -> verify(fetchPublishersPort, times(0)).fetchPublisher(any(PublisherIdentifier.class), any(RoomID.class))
        );
    }

    @Test
    void shouldGetPublishersAsMember() {
        // When
        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).managerId(UUIDGenerator.randomUUID()).build();
        var publisherIDs = List.of(UUIDGenerator.randomUUID(), UUIDGenerator.randomUUID());
        var publishers = List.of(createDummyPublisher(), createDummyPublisher());
        var pageRequest = emptyPageRequest();
        var result = Page.<PublisherView>builder().content(publishers).build();

        when(fetchRoomsPort.fetchRoom("PatientPortal")).thenReturn(Optional.of(room));
        when(fetchManagersPort.fetchManagerID(any(ManagerIdentifier.class))).thenReturn(Optional.of(managerID));
        when(fetchPublishersPort.fetchPublisher(any(PublisherIdentifier.class), any(RoomID.class))).thenReturn(publisherIDs);
        when(fetchPublishersPort.fetchPublishersView(publisherIDs, pageRequest)).thenReturn(result);

        // Then
        Assertions.assertAll(
            () -> {
                var request = new Request(new MemberIdentifier("EE/GOV/123456"), new RoomIdentifier("PatientPortal"), pageRequest);
                var response = getPublishersService.execute(request);
                assertThat(response.publishers().getContent(), hasSize(2));
            },
            () -> verify(fetchPublishersPort, times(0)).fetchPublishersView(any(RoomID.class), eq(pageRequest)),
            () -> {
                var captor = ArgumentCaptor.forClass(ManagerIdentifier.class);
                verify(fetchManagersPort).fetchManagerID(captor.capture());
                assertThat(captor.getValue().getIdentifier(), equalTo("EE/GOV/123456"));
            },
            () -> {
                var roomCaptor = ArgumentCaptor.forClass(String.class);
                verify(fetchRoomsPort).fetchRoom(roomCaptor.capture());
                assertThat(roomCaptor.getValue(), equalTo("PatientPortal"));
            },
            () -> {
                var publisherCaptor = ArgumentCaptor.forClass(PublisherIdentifier.class);
                var roomCaptor = ArgumentCaptor.forClass(RoomID.class);
                verify(fetchPublishersPort).fetchPublisher(publisherCaptor.capture(), roomCaptor.capture());
                assertThat(publisherCaptor.getValue().getIdentifier(), equalTo("EE/GOV/123456"));
                assertThat(roomCaptor.getValue().getId(), equalTo(roomId));
            }
        );
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // When
        when(fetchRoomsPort.fetchRoom(anyString())).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
            () -> {
                var request = new Request(new MemberIdentifier("EE/GOV/123456"), new RoomIdentifier("PatientPortal"), emptyPageRequest());
                assertThatThrownBy(() -> getPublishersService.execute(request))
                    .isInstanceOf(RoomNotFoundException.class)
                    .hasMessage("Room was not found");
            },
            () -> verifyNoInteractions(fetchPublishersPort, fetchManagersPort)
        );
    }

    private static PublisherView createDummyPublisher() {
        return PublisherView.builder().build();
    }

    private static PageRequest emptyPageRequest() {
        return PageRequest.builder().build();
    }
}
