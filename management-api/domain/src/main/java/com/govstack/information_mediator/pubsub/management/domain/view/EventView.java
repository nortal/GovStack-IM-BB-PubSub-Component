package com.govstack.information_mediator.pubsub.management.domain.view;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class EventView {
    private UUID id;
    private String correlationId;
    private Instant createdAt;
    private UUID publisherId;
    private String publisherIdentifier;
    private String eventTypeIdentifier;
    private Integer eventTypeVersionNo;
}
