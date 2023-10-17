package com.govstack.information_mediator.pubsub.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class PublishedMessage {
    private UUID id;
    private UUID eventId;
    private UUID subscriptionId;
    private Instant publishedAt;
    private Instant deliveredAt;
}
