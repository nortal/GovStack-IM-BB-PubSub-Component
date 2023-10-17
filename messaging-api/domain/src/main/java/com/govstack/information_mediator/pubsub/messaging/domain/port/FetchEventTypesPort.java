package com.govstack.information_mediator.pubsub.messaging.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import com.govstack.information_mediator.pubsub.domain.entity.Publisher;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;

import java.util.Optional;

public interface FetchEventTypesPort {

    Optional<EventType> fetchEventType(Publisher publisher, EventTypeIdentifier eventTypeIdentifier);
}
