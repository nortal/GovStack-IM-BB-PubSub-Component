package com.govstack.information_mediator.pubsub.management.web.publisher;

import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.management.web.validation.CreatePublisherValidation;
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
@CreatePublisherValidation
@Schema(name = "CreatePublisherResponse", description = "Create Publisher Response")
public class CreatePublisherResponseDTO {

    @Schema(description = "The publisher ID", example = "77fd07b4-1bb7-47bc-8d9a-6ca4af971be6", requiredMode = REQUIRED)
    private UUID publisherId;

    static CreatePublisherResponseDTO fromDomain(PublisherID publisherID) {
        return CreatePublisherResponseDTO.builder()
                .publisherId(publisherID.getId())
                .build();
    }
}
