package com.govstack.information_mediator.pubsub.management.web.event;

import com.govstack.information_mediator.pubsub.management.domain.view.EventView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "EventView", description = "Event view data")
public class EventViewDTO {
    private UUID id;
    private String correlationId;
    private Instant createdAt;
    private UUID publisherId;
    private String publisherIdentifier;
    private String eventTypeIdentifier;
    private Integer eventTypeVersionNo;
}
