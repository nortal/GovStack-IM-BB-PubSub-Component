package com.govstack.information_mediator.pubsub.messaging.web.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.UUID;

@Data
@NoArgsConstructor
@Schema(name = "PublishEventResponse", description = "Publish Event Response")
public class PublishEventResponseDTO {

    @Schema(description = "The event ID", required = true)
    private UUID eventId;
}
