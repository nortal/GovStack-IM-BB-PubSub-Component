package com.govstack.information_mediator.pubsub.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EventMessage {
    private UUID eventId;
    private UUID subscriptionId;
    private String payload;
    private boolean hasAnother;
}
