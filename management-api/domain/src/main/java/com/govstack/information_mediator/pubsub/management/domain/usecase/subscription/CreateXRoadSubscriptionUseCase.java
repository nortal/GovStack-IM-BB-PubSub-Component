package com.govstack.information_mediator.pubsub.management.domain.usecase.subscription;

import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

public interface CreateXRoadSubscriptionUseCase {

    Response execute(Request request);

    @Value
    @Builder
    class Request {
        RoomIdentifier room;
        EventTypeIdentifier eventType;
        SubscriptionData subscription;

        @Value
        @Builder
        public static class SubscriptionData {
            String identifier;
            IdentifierType identifierType;
            Parameters parameters;
            String status;

            @Value
            @Builder
            public static class Parameters {
                String method;
                String pushUrl;
                Integer deliveryDelay;
                Double deliveryDelayMultiplier;
                Integer deliveryAttempts;
            }
        }
    }

    @Value
    @RequiredArgsConstructor(staticName = "of")
    class Response {
        UUID subscriptionId;
    }
}
