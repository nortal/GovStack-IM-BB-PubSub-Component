package com.govstack.information_mediator.pubsub.domain.view;

import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class SubscriptionEventDeliveryView {
    private UUID eventId;
    private String eventTypeIdentifier;
    private Instant eventCreatedAt;
    private DeliveryStatus deliveryStatus;
    private Integer deliveryAttempts;

    public enum DeliveryStatus {
        PUBLISHED,
        PENDING,
        UNCONSUMED,
        DELIVERED
    }
}
