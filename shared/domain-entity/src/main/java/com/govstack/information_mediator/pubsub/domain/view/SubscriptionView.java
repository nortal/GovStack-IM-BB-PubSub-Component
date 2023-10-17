package com.govstack.information_mediator.pubsub.domain.view;

import com.govstack.information_mediator.pubsub.domain.entity.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class SubscriptionView {
    private UUID id;
    private Room room;
    private EventType eventType;
    private String identifier;
    private IdentifierType identifierType;
    private Subscription.Parameters parameters;
    private Subscription.Status status;
    private Instant createdAt;
    private String createdBy;
}
