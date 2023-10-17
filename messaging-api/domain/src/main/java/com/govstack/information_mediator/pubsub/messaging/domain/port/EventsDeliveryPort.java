package com.govstack.information_mediator.pubsub.messaging.domain.port;

import com.fasterxml.jackson.databind.JsonNode;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;

public interface EventsDeliveryPort {

    void deliverEventPayload(Subscription subscription, JsonNode payload);
}
