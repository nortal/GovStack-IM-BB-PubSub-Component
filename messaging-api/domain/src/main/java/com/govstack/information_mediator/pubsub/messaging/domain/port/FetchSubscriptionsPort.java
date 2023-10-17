package com.govstack.information_mediator.pubsub.messaging.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FetchSubscriptionsPort {

    Optional<Subscription> fetchSubscription(UUID subscriptionId);

    List<Subscription> fetchSubscriptions(RoomID roomID, EventTypeID eventTypeID);
}
