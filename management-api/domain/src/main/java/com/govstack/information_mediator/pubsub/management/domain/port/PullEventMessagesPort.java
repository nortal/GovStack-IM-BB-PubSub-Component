package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.EventMessage;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;

import java.util.Optional;

public interface PullEventMessagesPort {

    Optional<EventMessage> pullEventMessage(Room room, Subscription subscription);
}
