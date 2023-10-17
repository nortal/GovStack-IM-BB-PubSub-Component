package com.govstack.information_mediator.pubsub.management.web.publisher;

import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.usecase.publisher.CreatePublisherUseCae.Request;
import com.govstack.information_mediator.pubsub.management.domain.usecase.publisher.CreatePublisherUseCae.Request.PublisherData;
import com.govstack.information_mediator.pubsub.management.web.validation.CreatePublisherValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.EnumUtils;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CreatePublisherValidation
@Schema(name = "CreatePublisherRequest", description = "Create Publisher Request")
public class CreatePublisherDTO {

    @Schema(description = "Identifies the publisher", example = "EE/BUSINESS/123456789", requiredMode = REQUIRED)
    private String identifier;

    @Schema(description = "The identifier type", example = "XROAD",  allowableValues = { "INTERNAL", "XROAD"  }, requiredMode = REQUIRED)
    private String identifierType;

    @Schema(description = "The publisher's event types", example = "[\"newPatient\",\"delivery\"]", requiredMode = REQUIRED)
    private List<String> eventTypes;

    Request toRequest(ManagerIdentifier manager, RoomIdentifier room) {
        return new Request(manager, room, toPublisherData());
    }

    private PublisherData toPublisherData() {
        return new PublisherData(
                identifier,
                EnumUtils.getEnum(IdentifierType.class, identifierType),
                eventTypes);
    }
}
