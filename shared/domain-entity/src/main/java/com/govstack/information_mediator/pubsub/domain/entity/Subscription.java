package com.govstack.information_mediator.pubsub.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Subscription {
    private UUID id;
    private UUID roomId;
    private UUID eventTypeId;
    private String identifier;
    private IdentifierType identifierType;
    private Parameters parameters;
    private Status status;

    @Data
    @Builder
    public static class Parameters {
        private Method method;
        private String pushUrl;
        private Integer deliveryDelay;
        private Double deliveryDelayMultiplier;
        private Integer deliveryAttempts;
    }

    public enum Method {
        PUSH, PULL
    }

    public enum Status {
        PENDING,
        ACTIVE,
        REJECTED,
        TERMINATED
    }
}
