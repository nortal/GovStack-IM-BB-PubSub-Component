package com.govstack.information_mediator.pubsub.management.domain.entity;

import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class EventDetails {
    private Event event;
    private EventType eventType;
    private EventTypeVersion eventTypeVersion;
    private Publisher publisher;
    private List<Subscription> subscriptions;

    @Value
    @Builder
    public static class Event {
        UUID id;
        String correlationId;
        Instant createdAt;
    }

    @Value
    @Builder
    public static class EventType {
        UUID id;
        String identifier;
        Instant deletedAt;
    }

    @Value
    @Builder
    public static class EventTypeVersion {
        UUID id;
        Integer versionNo;
        String schema;
        Instant deletedAt;
    }

    @Value
    @Builder
    public static class Publisher {
        UUID id;
        String identifier;
        IdentifierType identifierType;
        Instant deletedAt;
    }

    @Value
    @Builder
    public static class Subscription {
        UUID id;
        String identifier;
        IdentifierType identifierType;
        Status status;
        Boolean isDelivered;
        Integer deliveryAttempts;
        @Builder.Default
        List<EventDelivery> deliveries = new ArrayList<>();
        Instant deletedAt;
    }

    @Value
    @Builder
    public static class EventDelivery {
        Instant publishedAt;
        Instant deliveredAt;
    }
}
