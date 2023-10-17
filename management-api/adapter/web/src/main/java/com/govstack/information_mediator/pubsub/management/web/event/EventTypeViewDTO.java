package com.govstack.information_mediator.pubsub.management.web.event;

import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "EventTypeView", description = "Event Type View")
public class EventTypeViewDTO {

    @Schema(description = "The event type ID", example = "77fd07b4-1bb7-47bc-8d9a-6ca4af971be6", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "The event type name", example = "newPatient", requiredMode = REQUIRED)
    private String identifier;

    @Schema(description = "Identifies when the event type was created", example = "2023-08-22 13:32:31.199 +0300", requiredMode = REQUIRED)
    private Instant createdAt;

    @Schema(description = "Identifies who created the event type", example = "jane.doe", requiredMode = REQUIRED)
    private String createdBy;

    @Schema(description = "Identifies the number of versions available for this event type", example = "1")
    private Integer versions;

    public static EventTypeViewDTO fromDomain(EventTypeView eventType) {
        return EventTypeViewDTO.builder()
            .id(eventType.getId())
            .identifier(eventType.getIdentifier())
            .createdAt(eventType.getCreatedAt())
            .createdBy(eventType.getCreatedBy())
            .versions(eventType.getVersions())
            .build();
    }
}
