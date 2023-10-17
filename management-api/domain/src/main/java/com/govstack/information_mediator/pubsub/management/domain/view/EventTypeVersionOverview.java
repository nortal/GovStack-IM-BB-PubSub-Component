package com.govstack.information_mediator.pubsub.management.domain.view;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
public class EventTypeVersionOverview {
    UUID id;
    UUID eventTypeId;
    Integer versionNo;
    String jsonSchema;
    Instant createdAt;
    String createdBy;
    Instant modifiedAt;
    String modifiedBy;
}
