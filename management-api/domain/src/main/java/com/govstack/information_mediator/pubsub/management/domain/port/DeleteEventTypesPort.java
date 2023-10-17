package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeVersionID;

public interface DeleteEventTypesPort {

    void deleteEventType(EventType eventType);

    void deleteEventTypeVersion(EventTypeVersionID eventTypeVersionID);
}
