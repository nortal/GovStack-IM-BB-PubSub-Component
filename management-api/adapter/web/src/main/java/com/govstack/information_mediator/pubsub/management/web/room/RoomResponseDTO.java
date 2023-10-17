package com.govstack.information_mediator.pubsub.management.web.room;

import com.govstack.information_mediator.pubsub.domain.view.RoomView;
import com.govstack.information_mediator.pubsub.management.web.validation.CreateRoomValidation;
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
@CreateRoomValidation
@Schema(name = "RoomResponse", description = "Room main information")
public class RoomResponseDTO {

    @Schema(description = "Identifier of the room", example = "emergencyNotifications", requiredMode = REQUIRED)
    private String identifier;

    @Schema(description = "Identifier of the manager of this room", example = "EE/BUSINESS/123456789", requiredMode = REQUIRED)
    private String managerIdentifier;

    static RoomResponseDTO fromView(RoomView roomView) {
        return RoomResponseDTO.builder()
                .identifier(roomView.getIdentifier())
                .managerIdentifier(roomView.getManagerIdentifier())
                .build();
    }
}
