package com.govstack.information_mediator.pubsub.messaging.web.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.govstack.information_mediator.pubsub.messaging.web.validation.PublishEventValidation;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@Schema(name = "PublishEvent", description = "Publish Event")
@PublishEventValidation
public class PublishEventDTO {

    @Schema(description = "Identifies the event type", example = "newPatient", required = true)
    private String eventType;

    @Schema(description = "The content (payload) of the event in JSON format", example = "{\"foo\":\"bar\"}", required = true)
    private JsonNode content;
}
