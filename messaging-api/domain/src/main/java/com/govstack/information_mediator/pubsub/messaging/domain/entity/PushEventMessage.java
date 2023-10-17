package com.govstack.information_mediator.pubsub.messaging.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class PushEventMessage {
    private UUID eventId;
    private UUID roomId;
    private UUID eventTypeId;
    private UUID eventTypeVersionId;
    private UUID publisherId;
    private UUID subscriptionId;

    private Integer deliveryAttempt;
    private Long timeToLive;

    private String correlationId;
    private String payload;
}
