package com.govstack.information_mediator.pubsub.management.web.subscription;

import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.view.SubscriptionView;
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
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "SubscriptionListItem", description = "Subscriptions list item")
public class SubscriptionResponseDTO {

    @Schema(description = "Identifies the event type", example = "00000000-0000-0000-0000-000000000000")
    private UUID id;

    @Schema(description = "Describes the subscription's identifier", example = "EE/BUSINESS/123")
    private String identifier;

    @Schema(description = "Describes the subscription's identifier type", example = "newEvent")
    private String eventTypeIdentifier;

    @Schema(description = "Identifies subscription type as either being PULL or PUSH", example = "PUSH", allowableValues = {"PUSH", "PULL"}, requiredMode = REQUIRED)
    private String method;

    @Schema(description = "Path to the listening endpoint in the subsystem for the callback to push messages. Required for PUSH configuration, ignored for PULL.", example = "/app/callback")
    private String pushUrl;

    @Schema(description = "Identifies subscription status", example = "ACTIVE", enumAsRef = true)
    private Subscription.Status status;

    @Schema(description = "When the subscription was created", example = "2023-08-22 13:32:31.199 +0300", requiredMode = REQUIRED)
    Instant createdAt;

    public static SubscriptionResponseDTO fromView(SubscriptionView subscriptionView) {
        return SubscriptionResponseDTO.builder()
                .id(subscriptionView.getId())
                .identifier(subscriptionView.getIdentifier())
                .eventTypeIdentifier(subscriptionView.getEventType().getIdentifier())
                .method(subscriptionView.getParameters().getMethod().name())
                .pushUrl(subscriptionView.getParameters().getPushUrl())
                .status(subscriptionView.getStatus())
                .createdAt(subscriptionView.getCreatedAt())
                .build();
    }
}
