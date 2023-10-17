package com.govstack.information_mediator.pubsub.management.web.manager;

import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.management.domain.usecase.manager.CreateManagersUseCase.Request;
import com.govstack.information_mediator.pubsub.management.web.validation.CreateManagerValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.EnumUtils;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CreateManagerValidation
@Schema(name = "CreateManagerRequest", description = "Create Manager Request")
public class CreateManagerDTO {

    @Schema(description = "Identifies the manager", example = "EE/BUSINESS/123456789", requiredMode = REQUIRED)
    private String identifier;

    @Schema(description = "The identifier type", example = "XROAD",  allowableValues = { "INTERNAL", "XROAD"  }, requiredMode = REQUIRED)
    private String identifierType;

    Request toRequest() {
        return new Request(identifier, EnumUtils.getEnum(IdentifierType.class, identifierType));
    }
}
