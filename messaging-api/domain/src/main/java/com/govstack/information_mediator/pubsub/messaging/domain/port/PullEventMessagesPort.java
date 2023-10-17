package com.govstack.information_mediator.pubsub.messaging.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.EventMessage;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;

import java.util.Optional;

public interface PullEventMessagesPort {

    Optional<EventMessage> pullEventMessage(Subscription subscription);
}
