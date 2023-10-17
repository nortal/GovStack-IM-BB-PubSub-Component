package com.govstack.information_mediator.pubsub.messaging.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.Event;

import java.util.UUID;

public interface EventsPort {

    UUID createEvent(Event event);

    boolean containsEvent(UUID eventId);
}
