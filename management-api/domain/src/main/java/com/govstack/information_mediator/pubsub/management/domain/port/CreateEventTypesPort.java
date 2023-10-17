package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import com.govstack.information_mediator.pubsub.domain.entity.EventType.Version;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeVersionID;
import com.networknt.schema.JsonSchema;

public interface CreateEventTypesPort {

    void createEventType(EventType eventType);

    void createEventTypeVersion(Version version, EventTypeID eventTypeID);

    void updateEvenTypeVersion(EventTypeVersionID versionID, JsonSchema jsonSchema);
}
