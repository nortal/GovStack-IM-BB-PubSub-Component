package com.govstack.information_mediator.pubsub.management.web.room;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.govstack.information_mediator.pubsub.management.domain.usecase.room.CreateRoomUseCase.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CreateRoomResponse", description = "Create Room Response")
class CreateRoomResponseDTO {

    @Schema(description = "The room unique id", example = "9546f939-8873-4419-a15d-e0276cc3eb0c")
    private UUID roomId;

    static CreateRoomResponseDTO from(Response response) {
        return CreateRoomResponseDTO.builder()
            .roomId(response.getRoomId())
            .build();
    }
}
