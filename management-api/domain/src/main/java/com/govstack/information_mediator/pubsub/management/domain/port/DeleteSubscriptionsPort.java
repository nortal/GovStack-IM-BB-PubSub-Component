package com.govstack.information_mediator.pubsub.management.domain.port;

import java.util.UUID;

public interface DeleteSubscriptionsPort {

    void terminateSubscription(UUID subscriptionId);

}
