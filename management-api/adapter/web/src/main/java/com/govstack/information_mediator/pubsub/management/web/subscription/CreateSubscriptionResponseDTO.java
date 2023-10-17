package com.govstack.information_mediator.pubsub.management.web.subscription;

import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.CreateXRoadSubscriptionUseCase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CreateSubscriptionResponse", description = "Create Subscription Response")
class CreateSubscriptionResponseDTO {

    @Schema(description = "The subscription id", example = "9546f939-8873-4419-a15d-e0276cc3eb0c", requiredMode = REQUIRED)
    private UUID subscriptionId;

    static CreateSubscriptionResponseDTO from(CreateXRoadSubscriptionUseCase.Response response) {
        return CreateSubscriptionResponseDTO.builder()
            .subscriptionId(response.getSubscriptionId())
            .build();
    }
}
