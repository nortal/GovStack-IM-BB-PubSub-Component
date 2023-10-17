package com.govstack.information_mediator.pubsub.management.web.manager;

import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Manager Response", name = "ManagerResponse")
class ManagerResponseDTO {

    private UUID id;

    @Schema(description = "Identifies the manager", example = "EE/BUSINESS/123456789", requiredMode = REQUIRED)
    private String identifier;

    @Schema(description = "The identifier type", example = "XROAD", allowableValues = { "INTERNAL", "XROAD"  }, requiredMode = REQUIRED)
    private String identifierType;

    static ManagerResponseDTO fromDomain(Manager manager) {
        return ManagerResponseDTO.builder()
            .id(manager.getId())
            .identifier(manager.getIdentifier())
            .identifierType(manager.getIdentifierType().name())
            .build();
    }
}
