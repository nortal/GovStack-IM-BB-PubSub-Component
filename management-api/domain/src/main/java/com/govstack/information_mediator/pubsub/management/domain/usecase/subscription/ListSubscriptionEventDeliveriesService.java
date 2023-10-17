package com.govstack.information_mediator.pubsub.management.domain.usecase.subscription;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.SubscriptionNotFoundException;
import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.id.SubscriptionID;
import com.govstack.information_mediator.pubsub.domain.view.SubscriptionEventDeliveryView;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchSubscriptionsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.ListSubscriptionEventsDeliveriesUseCase.Request.MemberData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.ListSubscriptionEventsDeliveriesUseCase.Request.RoomData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;


@Component
@RequiredArgsConstructor
class ListSubscriptionEventDeliveriesService implements ListSubscriptionEventsDeliveriesUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;
    private final FetchSubscriptionsPort fetchSubscriptionsPort;

    @Override
    @Transactional
    public Response execute(Request request) {
        var room = fetchRoom(request.getRoom());
        fetchManager(request.getMember());
        var subscription = fetchSubscription(request.getSubscriptionId(), room);
        return new Response(fetchSubscriptionEventDeliveries(subscription, request.getPageRequest(), request.getFromDate(),
            request.getToDate(), request.getEventId(), request.getEventTypeIdentifier(), request.getDeliveryStatus()));
    }

    private Room fetchRoom(RoomData roomData) {
        return fetchRoomsPort.fetchRoom(roomData.getIdentifier())
            .orElseThrow(RoomNotFoundException::new);
    }

    private void fetchManager(MemberData memberData) {
        fetchManagersPort.fetchManager(memberData.getIdentifier())
            .orElseThrow(ManagerNotFoundException::new);
    }

    private Subscription fetchSubscription(UUID subscriptionId, Room room) {
        return fetchSubscriptionsPort.fetchSubscription(new SubscriptionID(subscriptionId), new RoomID(room.getId()))
            .orElseThrow(SubscriptionNotFoundException::new);
    }

    private Page<SubscriptionEventDeliveryView> fetchSubscriptionEventDeliveries(Subscription subscription,
                                                                                 PageRequest pageRequest,
                                                                                 Instant fromDate,
                                                                                 Instant toDate,
                                                                                 UUID eventId,
                                                                                 String eventTypeIdentifier,
                                                                                 String deliveryStatus) {
        return fetchSubscriptionsPort.fetchSubscriptionEventDeliveries(subscription, pageRequest, fromDate, toDate,
            eventId, eventTypeIdentifier, deliveryStatus);
    }
}
