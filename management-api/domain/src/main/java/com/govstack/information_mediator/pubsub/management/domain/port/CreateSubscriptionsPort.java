package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.Subscription;

import java.util.UUID;

public interface CreateSubscriptionsPort {

    UUID createSubscription(Subscription subscription);

}
