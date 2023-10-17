package com.govstack.information_mediator.pubsub.management.web.subscription;

import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UpdateSubscriptionStatusRequest", description = "Approve or reject a pending subscription")
public class SubscriptionStatusDTO {

    @Schema(description = "Subscription status to be set", example = "ACTIVE", enumAsRef = true, requiredMode = REQUIRED)
    private Subscription.Status status;

}
