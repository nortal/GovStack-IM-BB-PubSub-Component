package com.govstack.information_mediator.pubsub.management.domain.view;

import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PublisherView {
    UUID id;
    String identifier;
    IdentifierType identifierType;
    Instant createdAt;
    String createdBy;

    @Builder.Default
    List<Constraint> constraints = new ArrayList<>();

    @Value
    @Builder
    public static class Constraint {
        UUID eventTypeId;
        String eventTypeIdentifier;
        Instant createdAt;
    }
}
