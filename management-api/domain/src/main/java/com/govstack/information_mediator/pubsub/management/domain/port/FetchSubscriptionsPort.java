package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.id.SubscriptionID;
import com.govstack.information_mediator.pubsub.domain.view.SubscriptionEventDeliveryView;
import com.govstack.information_mediator.pubsub.domain.view.SubscriptionView;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface FetchSubscriptionsPort {

    Optional<Subscription> fetchSubscription(SubscriptionID subscriptionID, RoomID roomID);

    Optional<Subscription> fetchSubscription(UUID subscriptionId, String identifier, Room room);

    Page<SubscriptionView> fetchAllSubscriptionViews(Room room, Subscription.Status status, PageRequest pageRequest);

    Page<SubscriptionView> fetchAllSubscriptionViews(Room room, String memberIdentifier,
                                                     Subscription.Status status,
                                                     PageRequest pageRequest);

    Page<SubscriptionEventDeliveryView> fetchSubscriptionEventDeliveries(Subscription subscription,
                                                                         PageRequest pageRequest,
                                                                         Instant fromDate,
                                                                         Instant toDate,
                                                                         UUID eventId,
                                                                         String eventTypeIdentifier,
                                                                         String deliveryStatus);
}
