package com.govstack.information_mediator.pubsub.management.domain.usecase.subscription;

import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.view.SubscriptionView;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchSubscriptionsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.ListSubscriptionsUseCase.Request;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.ListSubscriptionsUseCase.Request.RoomData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.ListSubscriptionsUseCase.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListSubscriptionsServiceTest {

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @Mock
    private FetchManagersPort fetchManagersPort;

    @Mock
    private FetchSubscriptionsPort fetchSubscriptionsPort;

    @InjectMocks
    private ListSubscriptionsService listSubscriptionsService;

    private RoomData roomData;
    private Request.MemberData memberData;
    private Room room;
    private Manager manager;
    private List<SubscriptionView> subscriptions;
    private Page<SubscriptionView> page;


    @BeforeEach
    void setUp() {
        UUID managerID = UUID.randomUUID();
        roomData = RoomData.of("room-identifier");
        memberData = Request.MemberData.of("member-identifier");
        room = Room.builder().managerId(managerID).build();
        manager = Manager.builder().id(managerID).build();
        subscriptions = Arrays.asList(
            SubscriptionView.builder().build(),
            SubscriptionView.builder().build()
        );
        page = Page.<SubscriptionView>builder().content(subscriptions).build();
    }

    @Test
    void shouldFetchSubscriptionsSuccessfullyWhenManagerIdMatches() {
        var request = ListSubscriptionsUseCase.Request.of(roomData, memberData, null, PageRequest.builder().build());

        when(fetchRoomsPort.fetchRoom(roomData.getIdentifier())).thenReturn(Optional.of(room));
        when(fetchManagersPort.fetchManager(memberData.getIdentifier())).thenReturn(Optional.of(manager));
        when(fetchSubscriptionsPort.fetchAllSubscriptionViews(room, null, request.getPageRequest())).thenReturn(page);

        Response response = listSubscriptionsService.execute(request);
        Assertions.assertEquals(subscriptions, response.subscriptions().getContent());
    }

    @Test
    void shouldFetchSubscriptionsSuccessfullyWhenManagerIdDoesNotMatch() {
        var request = ListSubscriptionsUseCase.Request.of(roomData, memberData, null, PageRequest.builder().build());
        manager = Manager.builder().id(UUID.randomUUID()).build();

        when(fetchRoomsPort.fetchRoom(roomData.getIdentifier())).thenReturn(Optional.of(room));
        when(fetchManagersPort.fetchManager(memberData.getIdentifier())).thenReturn(Optional.of(manager));
        when(fetchSubscriptionsPort.fetchAllSubscriptionViews(room, memberData.getIdentifier(), null, request.getPageRequest())).thenReturn(page);

        Response response = listSubscriptionsService.execute(request);
        Assertions.assertEquals(subscriptions, response.subscriptions().getContent());
    }

    @Test
    void shouldFetchSubscriptionsSuccessfullyWhenManagerNotFound() {
        var request = ListSubscriptionsUseCase.Request.of(roomData, memberData, null, PageRequest.builder().build());

        when(fetchRoomsPort.fetchRoom(roomData.getIdentifier())).thenReturn(Optional.of(room));
        when(fetchManagersPort.fetchManager(memberData.getIdentifier())).thenReturn(Optional.empty());
        when(fetchSubscriptionsPort.fetchAllSubscriptionViews(room, memberData.getIdentifier(), null, request.getPageRequest())).thenReturn(page);

        Response response = listSubscriptionsService.execute(request);
        Assertions.assertEquals(subscriptions, response.subscriptions().getContent());
    }

    @ParameterizedTest
    @EnumSource(Subscription.Status.class)
    void shouldHandleSubscriptionStatusesCorrectly(Subscription.Status status) {
        var request = ListSubscriptionsUseCase.Request.of(roomData, memberData, status, PageRequest.builder().build());

        when(fetchRoomsPort.fetchRoom(roomData.getIdentifier())).thenReturn(Optional.of(room));
        when(fetchManagersPort.fetchManager(memberData.getIdentifier())).thenReturn(Optional.of(manager));
        when(fetchSubscriptionsPort.fetchAllSubscriptionViews(room, status, request.getPageRequest())).thenReturn(page);

        Response response = listSubscriptionsService.execute(request);
        Assertions.assertEquals(subscriptions, response.subscriptions().getContent());
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        var request = ListSubscriptionsUseCase.Request.of(roomData, memberData, null, PageRequest.builder().build());
        when(fetchRoomsPort.fetchRoom(roomData.getIdentifier())).thenReturn(Optional.empty());

        assertThatExceptionOfType(RoomNotFoundException.class)
            .isThrownBy(() -> listSubscriptionsService.execute(request));
    }
}
