package com.govstack.information_mediator.pubsub.management.web.event;

import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeVersionOverview;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "EventTypeVersion", description = "Event Type Version")
public class EventTypeVersionDTO {

    @Schema(description = "The version number", example = "1", requiredMode = REQUIRED)
    private Integer versionNo;

    @Schema(description = "The version json schema", requiredMode = REQUIRED)
    private String jsonSchema;

    @Schema(description = "Identifies when the version was created", example = "2023-07-25T03:38:51+0000", requiredMode = REQUIRED)
    private Instant createdAt;

    @Schema(description = "Identifies who created the version", example = "system-administrator", requiredMode = REQUIRED)
    private String createdBy;

    @Schema(description = "Identifies when the version was modified", example = "2023-07-25T03:38:51+0000")
    private Instant modifiedAt;

    @Schema(description = "Identifies who modified the event type", example = "system-administrator", requiredMode = REQUIRED)
    private String modifiedBy;

    public static EventTypeVersionDTO fromDomain(EventTypeVersionOverview eventTypeVersion) {
        return EventTypeVersionDTO.builder()
            .versionNo(eventTypeVersion.getVersionNo())
            .jsonSchema(eventTypeVersion.getJsonSchema())
            .createdAt(eventTypeVersion.getCreatedAt())
            .createdBy(eventTypeVersion.getCreatedBy())
            .modifiedAt(eventTypeVersion.getModifiedAt())
            .modifiedBy(eventTypeVersion.getModifiedBy())
            .build();
    }
}
