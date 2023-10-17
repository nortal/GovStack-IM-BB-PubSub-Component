package com.govstack.information_mediator.pubsub.management.domain.usecase.subscription;

import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.SubscriptionNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.id.SubscriptionID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchSubscriptionsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.SubscriptionStatusPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.PENDING;

@Component
@RequiredArgsConstructor
class ApproveSubscriptionService implements ApproveSubscriptionUseCase {

    private final Collection<Status> VALID_STATUSES_FOR_APPROVAL = Set.of(PENDING);

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchManagersPort fetchManagersPort;
    private final FetchSubscriptionsPort fetchSubscriptionsPort;
    private final SubscriptionStatusPort subscriptionStatusPort;

    @Override
    @Transactional
    public void execute(Request request) {
        var manager = fetchManager(request.manager());
        var room = fetchRoom(request.room(), manager);
        var subscription = fetchSubscription(request.subscriptionID(), room);
        approveSubscription(subscription);
    }

    private ManagerID fetchManager(ManagerIdentifier manager) {
        return fetchManagersPort.fetchManagerID(manager).orElseThrow(ManagerNotFoundException::new);
    }

    private RoomID fetchRoom(RoomIdentifier room, ManagerID managerID) {
        return fetchRoomsPort.fetchRoomID(room, managerID).orElseThrow(RoomNotFoundException::new);
    }

    private Subscription fetchSubscription(SubscriptionID subscriptionID, RoomID roomID) {
        return fetchSubscriptionsPort.fetchSubscription(subscriptionID, roomID)
            .orElseThrow(SubscriptionNotFoundException::new);
    }

    private void approveSubscription(Subscription subscription) {
        var canApproveSubscription = VALID_STATUSES_FOR_APPROVAL.contains(subscription.getStatus());
        if (canApproveSubscription) {
            subscriptionStatusPort.setSubscriptionStatus(subscription.getId(), Status.ACTIVE);
        } else {
            throw new ApiException("Subscription with status [" + subscription.getStatus() + "] can not be approved");
        }
    }
}
