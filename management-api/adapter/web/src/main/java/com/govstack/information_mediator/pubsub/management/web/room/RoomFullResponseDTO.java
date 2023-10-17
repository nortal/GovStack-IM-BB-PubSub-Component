package com.govstack.information_mediator.pubsub.management.web.room;

import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.management.domain.view.RoomDetailedView;
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
@Schema(name = "RoomFullResponse", description = "Detailed room information")
public class RoomFullResponseDTO {

    @Schema(description = "Identifier of the room", example = "emergencyNotifications", requiredMode = REQUIRED)
    private String identifier;

    @Schema(description = "Identifier of the manager of this room", example = "EE/BUSINESS/123456789", requiredMode = REQUIRED)
    private String managerIdentifier;

    @Schema(description = "Default duration in milliseconds after which messages should expire. If set to 'null', messages never expire.", example = "1000000", requiredMode = REQUIRED)
    private Integer messageExpiration;

    @Schema(description = "Default duration in milliseconds for delay between message delivery attempts for subscriptions in PUSH configuration, ignored for PULL. Can be overridden by subscriptions", minimum = "1", example = "1", requiredMode = REQUIRED)
    private Integer deliveryDelay;

    @Schema(description = "Default multiplier by which delivery delay is increased exponentially between delivery attempts. Can be overridden by subscriptions.", minimum = "1.01", example = "1.01", requiredMode = REQUIRED)
    private Double deliveryDelayMultiplier;

    @Schema(description = "Maximum number of delivery attempts for a message in PUSH configuration, ignored for PULL", example = "10", requiredMode = REQUIRED)
    private Integer deliveryAttempts;

    @Schema(description = "Identifies when the room was created", example = "2023-08-22 13:32:31.199 +0300", requiredMode = REQUIRED)
    Instant createdAt;

    @Schema(description = "Identifies who created the room", example = "jane.doe", requiredMode = REQUIRED)
    String createdBy;

    static RoomFullResponseDTO fromDetailedView(RoomDetailedView roomView, Manager manager) {
        return RoomFullResponseDTO.builder()
                .identifier(roomView.getIdentifier())
                .managerIdentifier(manager.getIdentifier())
                .messageExpiration(roomView.getConfiguration().getMessageExpiration())
                .deliveryDelay(roomView.getConfiguration().getDeliveryDelay())
                .deliveryDelayMultiplier(roomView.getConfiguration().getDeliveryDelayMultiplier())
                .deliveryAttempts(roomView.getConfiguration().getDeliveryAttempts())
                .createdAt(roomView.getCreatedAt())
                .createdBy(roomView.getCreatedBy())
                .build();
    }
}
