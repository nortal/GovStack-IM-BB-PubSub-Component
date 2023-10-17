package com.govstack.information_mediator.pubsub.messaging.domain.usecase.publishing;

import com.fasterxml.jackson.databind.JsonNode;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.PublisherIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

public interface PublishEventsUseCase {

    Response execute(Request request);

    @Value
    @Builder
    class Request {
        RoomIdentifier room;
        PublisherIdentifier publisher;
        EventTypeIdentifier eventType;
        JsonNode payload;
    }

    record Response(UUID eventId) {

    }
}
