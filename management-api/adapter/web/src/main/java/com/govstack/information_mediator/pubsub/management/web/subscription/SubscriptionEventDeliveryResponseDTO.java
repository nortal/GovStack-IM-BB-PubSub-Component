package com.govstack.information_mediator.pubsub.management.web.subscription;

import com.govstack.information_mediator.pubsub.domain.view.SubscriptionEventDeliveryView;
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
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "SubscriptionEventDeliveryItem", description = "Subscription event delivery item")
public class SubscriptionEventDeliveryResponseDTO {

    @Schema(description = "Event ID", example = "00000000-0000-0000-0000-000000000000")
    private UUID eventId;

    @Schema(description = "Describes the subscription's identifier type", example = "newEvent")
    private String eventTypeIdentifier;

    @Schema(description = "Status of the message for this event", example = "PENDING", enumAsRef = true, requiredMode = REQUIRED)
    private SubscriptionEventDeliveryView.DeliveryStatus deliveryStatus;

    @Schema(description = "Identifies how many times the message has been (or was) attempted to deliver", example = "2", requiredMode = REQUIRED)
    private Integer deliveryAttempts;

    @Schema(description = "When the event was created", example = "2023-08-22 13:32:31.199 +0300", requiredMode = REQUIRED)
    Instant createdAt;

    public static SubscriptionEventDeliveryResponseDTO fromView(SubscriptionEventDeliveryView eventDeliveryView) {
        return SubscriptionEventDeliveryResponseDTO.builder()
                .eventId(eventDeliveryView.getEventId())
                .eventTypeIdentifier(eventDeliveryView.getEventTypeIdentifier())
                .deliveryStatus(eventDeliveryView.getDeliveryStatus())
                .deliveryAttempts(eventDeliveryView.getDeliveryAttempts())
                .createdAt(eventDeliveryView.getEventCreatedAt())
                .build();
    }
}
