package com.govstack.information_mediator.pubsub.management.domain.port;

import java.util.UUID;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status;

public interface SubscriptionStatusPort {

    void setSubscriptionStatus(UUID subscriptionId, Status status);

}
