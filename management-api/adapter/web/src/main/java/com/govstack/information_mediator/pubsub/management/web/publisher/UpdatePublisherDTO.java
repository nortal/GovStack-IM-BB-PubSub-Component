package com.govstack.information_mediator.pubsub.management.web.publisher;

import com.govstack.information_mediator.pubsub.management.web.validation.UpdatePublisherValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@UpdatePublisherValidation
@Schema(name = "UpdatePublisherRequest", description = "Update Publisher Request")
public class UpdatePublisherDTO {

    @Schema(description = "Identifies the event types for the publisher", example = "[\"newPatient\",\"deliveries\"]", requiredMode = REQUIRED)
    private List<String> eventTypes;
}
