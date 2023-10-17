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
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.ListSubscriptionsUseCase.Request.MemberData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.ListSubscriptionsUseCase.Request.RoomData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
class ListSubscriptionsService implements ListSubscriptionsUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;
    private final FetchSubscriptionsPort fetchSubscriptionsPort;

    @Override
    @Transactional
    public Response execute(Request request) {
        var room = fetchRoom(request.getRoom());

        var subscriptions = fetchManager(request.getMember())
            .filter(manager -> manager.getId().equals(room.getManagerId()))
            .map(manager -> fetchSubscriptionsByRoom(room, request.getStatus(), request.getPageRequest()))
            .orElseGet(() -> fetchSubscriptionsByRoomAndIdentifier(room, request.getMember(), request.getStatus(), request.getPageRequest()));
        return new Response(subscriptions);
    }

    private Room fetchRoom(RoomData roomData) {
        return fetchRoomsPort.fetchRoom(roomData.getIdentifier())
            .orElseThrow(RoomNotFoundException::new);
    }

    private Optional<Manager> fetchManager(MemberData memberData) {
        return fetchManagersPort.fetchManager(memberData.getIdentifier());
    }

    private Page<SubscriptionView> fetchSubscriptionsByRoom(Room room, Subscription.Status status, PageRequest pageRequest) {
        return fetchSubscriptionsPort.fetchAllSubscriptionViews(room, status, pageRequest);
    }

    private Page<SubscriptionView> fetchSubscriptionsByRoomAndIdentifier(
        Room room,
        MemberData memberData,
        Subscription.Status status,
        PageRequest pageRequest) {
        return fetchSubscriptionsPort.fetchAllSubscriptionViews(room, memberData.getIdentifier(), status, pageRequest);
    }
}
