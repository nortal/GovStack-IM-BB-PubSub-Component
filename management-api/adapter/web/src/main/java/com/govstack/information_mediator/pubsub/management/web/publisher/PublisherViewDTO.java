package com.govstack.information_mediator.pubsub.management.web.publisher;

import com.govstack.information_mediator.pubsub.management.domain.view.PublisherView;
import com.govstack.information_mediator.pubsub.management.domain.view.PublisherView.Constraint;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "PublisherViewResponse", description = "Publisher View Response")
public class PublisherViewDTO {

    @Schema(description = "The publisher ID", example = "77fd07b4-1bb7-47bc-8d9a-6ca4af971be6", requiredMode = REQUIRED)
    UUID id;

    @Schema(description = "The publisher identifier", example = "EE/BUSINESS/123456789", requiredMode = REQUIRED)
    String identifier;

    @Schema(description = "The publisher type", example = "XROAD", allowableValues = { "XROAD", "INTERNAL"}, requiredMode = REQUIRED)
    String identifierType;

    @Schema(description = "Identifies when the publisher was created", example = "2023-08-22 13:32:31.199 +0300", requiredMode = REQUIRED)
    Instant createdAt;

    @Schema(description = "Identifies who created the publisher", example = "jane.doe", requiredMode = REQUIRED)
    String createdBy;

    @Schema(description = "The publisher constraints")
    List<PublisherConstraintViewDTO> constraints;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "PublisherConstraintView", description = "Publisher Constraint View")
    public static class PublisherConstraintViewDTO {

        @Schema(description = "The event type ID", example = "77fd07b4-1bb7-47bc-8d9a-6ca4af971be6", requiredMode = REQUIRED)
        UUID eventTypeId;

        @Schema(description = "The event type name", example = "newPatient", requiredMode = REQUIRED)
        String eventIdentifier;

        @Schema(description = "Identifies when the constraint was created", example = "2023-08-22 13:32:31.199 +0300", requiredMode = REQUIRED)
        Instant createdAt;

        public static PublisherConstraintViewDTO fromDomain(Constraint constraint) {
            return PublisherConstraintViewDTO.builder()
                .eventTypeId(constraint.getEventTypeId())
                .eventIdentifier(constraint.getEventTypeIdentifier())
                .createdAt(constraint.getCreatedAt())
                .build();
        }
    }

    public static PublisherViewDTO fromDomain(PublisherView overview) {
        return PublisherViewDTO.builder()
            .id(overview.getId())
            .identifier(overview.getIdentifier())
            .identifierType(overview.getIdentifierType().name())
            .constraints(overview.getConstraints().stream().map(PublisherConstraintViewDTO::fromDomain).toList())
            .createdAt(overview.getCreatedAt())
            .createdBy(overview.getCreatedBy())
            .build();
    }
}
