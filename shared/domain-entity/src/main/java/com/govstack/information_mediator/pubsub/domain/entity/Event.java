package com.govstack.information_mediator.pubsub.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class Event {
    private UUID id;
    private UUID roomId;
    private UUID eventTypeId;
    private UUID eventTypeVersionId;
    private UUID publisherId;
    private String correlationId;
    private Instant createdAt;
}
