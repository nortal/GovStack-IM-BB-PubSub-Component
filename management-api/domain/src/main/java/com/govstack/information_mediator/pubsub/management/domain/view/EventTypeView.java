package com.govstack.information_mediator.pubsub.management.domain.view;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
public class EventTypeView {
    UUID id;
    String identifier;
    Instant createdAt;
    String createdBy;
    Integer versions;
}
