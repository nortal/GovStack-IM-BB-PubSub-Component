package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.Event;

public interface EventPublisherPort {
    void publishEvent(Event event);
}
