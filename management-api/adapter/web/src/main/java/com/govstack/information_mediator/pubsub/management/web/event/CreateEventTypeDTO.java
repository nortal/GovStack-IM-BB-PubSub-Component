package com.govstack.information_mediator.pubsub.management.web.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request.EventTypeData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request.EventTypeData.VersionData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request.ManagerData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request.RoomData;
import com.govstack.information_mediator.pubsub.management.web.validation.CreateEventTypeValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CreateEventTypeValidation
@Schema(name = "CreateEventType", description = "Create Event Type")
public class CreateEventTypeDTO {

    @Schema(description = "Identifies the event type", example = "newPatient", requiredMode = REQUIRED)
    private String identifier;

    @Schema(description = "The version number", requiredMode = REQUIRED)
    private Integer versionNo;

    @Schema(description = "The version json schema", requiredMode = REQUIRED)
    private String schema;

    Request toRequest(String managerIdentifier, String roomIdentifier) {
        return Request.builder()
            .manager(ManagerData.of(managerIdentifier))
            .room(RoomData.of(roomIdentifier))
            .eventType(EventTypeData.builder()
                .identifier(identifier)
                .version(VersionData.of(versionNo, schema))
                .build())
            .build();
    }
}
