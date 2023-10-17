package com.govstack.information_mediator.pubsub.messaging.artemis;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
class ArtemisMessage {
    @Builder.Default
    Headers headers = Headers.defaultHeader();
    Properties properties;

    String destination;
    String payload;

    @Value
    @Builder
    static class Headers {
        @Builder.Default
        Long deliveryDelay = 0L;
        @Builder.Default
        Long timeToLive = 0L;

        private static Headers defaultHeader() {
            return Headers.builder().build();
        }
    }

    @Value
    @Builder
    static class Properties {
        UUID eventId;
        UUID roomId;
        UUID eventTypeId;
        UUID eventTypeVersionId;
        UUID publisherId;
        UUID subscriptionId;
        String correlationId;
        @Builder.Default
        Integer deliveryAttempt = 1;
    }
}
