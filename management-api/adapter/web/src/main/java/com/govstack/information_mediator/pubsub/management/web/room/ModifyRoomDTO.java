package com.govstack.information_mediator.pubsub.management.web.room;

import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.management.web.validation.ModifyRoomValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.govstack.information_mediator.pubsub.management.domain.usecase.room.ModifyRoomUseCase.Request;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ModifyRoomValidation
@Schema(name = "ModifyRoomRequest", description = "Modify Room")
public class ModifyRoomDTO {

    @Schema(description = "New identifier for the room", example = "emergencyNotifications")
    private String identifier;

    @Schema(description = "Identifier of the new manager of this room", example = "EE/BUSINESS/123456789")
    private String managerIdentifier;

    @Schema(description = "Default duration in milliseconds after which messages should expire. If set to 'null', messages never expire.", example = "1000000", requiredMode = REQUIRED)
    private Integer messageExpiration;

    @Schema(description = "Default duration in milliseconds for delay between message delivery attempts for subscriptions in PUSH configuration, ignored for PULL. Can be overridden by subscriptions", minimum = "1", example = "1", requiredMode = REQUIRED)
    private Integer deliveryDelay;

    @Schema(description = "Default multiplier by which delivery delay is increased exponentially between delivery attempts. Can be overridden by subscriptions.", minimum = "1.01", example = "1.01", requiredMode = REQUIRED)
    private Double deliveryDelayMultiplier;

    @Schema(description = "Maximum number of delivery attempts for a message in PUSH configuration, ignored for PULL", example = "10", requiredMode = REQUIRED)
    private Integer deliveryAttempts;


    Request toRequestWith(String currentManagerIdentifier, String currentRoomIdentifier) {
        return Request.builder()
            .room(Request.RoomData.builder()
                .identifier(currentRoomIdentifier)
                .build())
            .manager(Request.ManagerData.builder()
                .identifier(currentManagerIdentifier)
                .build())
            .newManagerIdentifier(managerIdentifier)
            .newRoomData(Room.builder()
                .identifier(identifier)
                .configuration(Room.Configuration.builder()
                    .deliveryDelay(deliveryDelay)
                    .deliveryDelayMultiplier(deliveryDelayMultiplier)
                    .deliveryAttempts(deliveryAttempts)
                    .messageExpiration(messageExpiration)
                    .build())
                .build())
            .build();
    }
}
