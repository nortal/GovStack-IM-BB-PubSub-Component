package com.govstack.information_mediator.pubsub.management.domain.view;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class RoomDetailedView {
    private UUID id;
    private String identifier;
    private String managerIdentifier;
    private Configuration configuration;
    Instant createdAt;
    String createdBy;

    @Data
    @Builder
    public static class Configuration {
        private Integer messageExpiration;
        private Integer deliveryDelay;
        private Double deliveryDelayMultiplier;
        private Integer deliveryAttempts;
    }
}
