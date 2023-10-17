package com.govstack.information_mediator.pubsub.management.web.event;

import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.UpdateEventTypeVersionUseCase.Request;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.UpdateEventTypeVersionUseCase.Request.VersionData;
import com.govstack.information_mediator.pubsub.management.web.validation.UpdateEventTypeVersionValidation;
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
@UpdateEventTypeVersionValidation
@Schema(name = "UpdateEventTypeVersion", description = "Update Event Type Version")
public class UpdateEventTypeVersionDTO {

    @Schema(description = "The version number", requiredMode = REQUIRED)
    private Integer versionNo;

    @Schema(description = "The version json schema", requiredMode = REQUIRED)
    private String schema;

    public Request toRequest(String manager, String room, String eventType) {
        return Request.builder()
            .managerIdentifier(new ManagerIdentifier(manager))
            .roomIdentifier(new RoomIdentifier(room))
            .eventTypeIdentifier(new EventTypeIdentifier(eventType))
            .version(VersionData.builder()
                .versionNo(versionNo)
                .jsonSchema(schema)
                .build())
            .build();
    }
}
