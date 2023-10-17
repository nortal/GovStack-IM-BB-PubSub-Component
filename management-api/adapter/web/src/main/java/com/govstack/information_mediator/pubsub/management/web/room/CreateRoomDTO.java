package com.govstack.information_mediator.pubsub.management.web.room;

import com.govstack.information_mediator.pubsub.management.web.validation.CreateRoomValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.govstack.information_mediator.pubsub.management.domain.usecase.room.CreateRoomUseCase.Request;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CreateRoomValidation
@Schema(name = "CreateRoomRequest", description = "Create Room")
public class CreateRoomDTO {

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

    Request toRequest() {
        return Request.builder()
            .room(Request.RoomData.builder()
                .identifier(identifier)
                .configuration(Request.ConfigurationData.builder()
                    .messageExpiration(messageExpiration)
                    .deliveryDelay(deliveryDelay)
                    .deliveryDelayMultiplier(deliveryDelayMultiplier)
                    .deliveryAttempts(deliveryAttempts)
                    .build())
                .build())
            .manager(Request.ManagerData.builder()
                .identifier(managerIdentifier)
                .build())
            .build();
    }
}
