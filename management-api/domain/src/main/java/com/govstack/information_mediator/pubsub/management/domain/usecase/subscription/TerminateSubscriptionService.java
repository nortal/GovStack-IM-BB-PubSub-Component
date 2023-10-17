package com.govstack.information_mediator.pubsub.management.domain.usecase.subscription;

import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.SubscriptionNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.id.SubscriptionID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.SubscriptionIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.DeleteSubscriptionsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchSubscriptionsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.ACTIVE;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.PENDING;


@Component
@RequiredArgsConstructor
class TerminateSubscriptionService implements TerminateSubscriptionUseCase {

    private final Collection<Status> VALID_STATUSES_FOR_TERMINATION = Set.of(PENDING, ACTIVE);

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;
    private final FetchSubscriptionsPort fetchSubscriptionsPort;
    private final DeleteSubscriptionsPort deleteSubscriptionsPort;

    @Override
    @Transactional
    public void execute(Request request) {
        var room = fetchRoom(request.room());

        var manager = fetchManager(request.member().asManager(), room);
        if (manager.isPresent()) {
            terminateSubscriptionAsManager(request.subscriptionID(), room);
        } else {
            terminateMemberSubscription(request.subscriptionID(), request.member().asSubscription(), room);
        }
    }

    private Room fetchRoom(RoomIdentifier room) {
        return fetchRoomsPort.fetchRoom(room.getIdentifier()).orElseThrow(RoomNotFoundException::new);
    }

    private Optional<ManagerID> fetchManager(ManagerIdentifier manager, Room room) {
        return fetchManagersPort.fetchManagerID(manager)
            .filter(managerID -> managerID.getId().equals(room.getManagerId()));
    }

    private void terminateSubscriptionAsManager(SubscriptionID subscriptionID, Room room) {
        terminateSubscription(fetchSubscription(subscriptionID, new RoomID(room.getId())));
    }

    private void terminateMemberSubscription(SubscriptionID subscriptionID, SubscriptionIdentifier subscriptionIdentifier, Room room) {
        terminateSubscription(fetchSubscription(subscriptionID, subscriptionIdentifier, room));
    }

    private Subscription fetchSubscription(SubscriptionID subscriptionID, RoomID roomID) {
        return fetchSubscriptionsPort.fetchSubscription(subscriptionID, roomID)
            .orElseThrow(SubscriptionNotFoundException::new);
    }
    private Subscription fetchSubscription(SubscriptionID subscriptionID, SubscriptionIdentifier subscriptionIdentifier, Room room) {
        return fetchSubscriptionsPort.fetchSubscription(subscriptionID.getId(), subscriptionIdentifier.getIdentifier(), room)
            .orElseThrow(SubscriptionNotFoundException::new);
    }

    private void terminateSubscription(Subscription subscription) {
        var canTerminateSubscription = VALID_STATUSES_FOR_TERMINATION.contains(subscription.getStatus());
        if (canTerminateSubscription) {
            deleteSubscriptionsPort.terminateSubscription(subscription.getId());
        } else {
            throw new ApiException("Subscription with status [" + subscription.getStatus() +"] can not be terminated");
        }
    }
}
