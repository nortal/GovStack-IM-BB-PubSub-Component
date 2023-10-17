package com.govstack.information_mediator.pubsub.messaging.domain.usecase.pulling;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public interface PullEventMessagesUseCase {

    Response execute(Request request);

    record Request(String roomIdentifier, String subscriptionIdentifier, UUID subscriptionId) {

    }

    record Response(JsonNode payload, boolean hasAnother) {

        public static Response empty() {
           return new Response(null, false);
        }
    }
}
