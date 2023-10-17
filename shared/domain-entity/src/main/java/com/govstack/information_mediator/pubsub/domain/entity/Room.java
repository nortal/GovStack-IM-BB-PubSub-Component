package com.govstack.information_mediator.pubsub.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Room {
    private UUID id;
    private UUID managerId;
    private String identifier;
    private Configuration configuration;

    @Data
    @Builder
    public static class Configuration {
        private Integer messageExpiration;
        private Integer deliveryDelay;
        private Double deliveryDelayMultiplier;
        private Integer deliveryAttempts;
    }
}
