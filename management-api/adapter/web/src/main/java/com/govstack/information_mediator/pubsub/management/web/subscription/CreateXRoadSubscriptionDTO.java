package com.govstack.information_mediator.pubsub.management.web.subscription;

import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.CreateXRoadSubscriptionUseCase.Request;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.CreateXRoadSubscriptionUseCase.Request.SubscriptionData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.CreateXRoadSubscriptionUseCase.Request.SubscriptionData.Parameters;
import com.govstack.information_mediator.pubsub.management.web.validation.CreateXRoadSubscriptionValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.govstack.information_mediator.pubsub.domain.entity.IdentifierType.XROAD;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CreateXRoadSubscriptionValidation
@Schema(name = "CreateXRoadSubscriptionRequest", description = "Create XRoad Subscription")
public class CreateXRoadSubscriptionDTO {

    @Schema(description = "Identifies the event type", example = "newPatient", requiredMode = REQUIRED)
    private String eventType;

    @Schema(description = "Identifies subscription type as either being PULL or PUSH", example = "PUSH", allowableValues = {"PUSH", "PULL"}, requiredMode = REQUIRED)
    private String method;

    @Schema(description = "Path to the listening endpoint in the subsystem for the callback to push messages. Required for PUSH configuration, ignored for PULL.", example = "/app/callback")
    private String pushUrl;

    @Schema(description = "Duration in milliseconds for delay between message delivery attempts. Optional and will override room default value in PUSH configuration, ignored for PULL.", minimum = "1", example = "1")
    private Integer deliveryDelay;

    @Schema(description = "Multiplier by which delivery delay is increased exponentially between delivery attempts. Optional and will override room default value in PUSH configuration, ignored for PULL.", minimum = "1.01", example = "1.01")
    private Double deliveryDelayMultiplier;

    @Schema(description = "Maximum number of delivery attempts for a message in PUSH configuration, ignored for PULL", example = "10")
    private Integer deliveryAttempts;

    Request toRequestWith(String subscription, String room) {
        return Request.builder()
            .room(new RoomIdentifier(room))
            .eventType(new EventTypeIdentifier(eventType))
            .subscription(SubscriptionData.builder()
                .identifier(subscription)
                .identifierType(XROAD)
                .parameters(Parameters.builder()
                    .method(method)
                    .pushUrl(pushUrl)
                    .deliveryDelay(deliveryDelay)
                    .deliveryDelayMultiplier(deliveryDelayMultiplier)
                    .deliveryAttempts(deliveryAttempts)
                    .build())
                .build())
            .build();
    }
}
