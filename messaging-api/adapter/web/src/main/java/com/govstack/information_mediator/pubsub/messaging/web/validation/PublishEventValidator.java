package com.govstack.information_mediator.pubsub.messaging.web.validation;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.schema.SchemaProvider;
import com.govstack.information_mediator.pubsub.commons.validation.JsonSchemaValidator;
import com.govstack.information_mediator.pubsub.messaging.web.event.PublishEventDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

@ApplicationScoped
@RequiredArgsConstructor
public class PublishEventValidator implements ConstraintValidator<PublishEventValidation, PublishEventDTO> {

    private final SchemaProvider schemaProvider = new SchemaProvider(Path.of("publish-event-schema.json"));

    private final JsonService jsonService;

    @Override
    public boolean isValid(PublishEventDTO value, ConstraintValidatorContext context) {
        var jsonNode = jsonService.valueToTree(value);
        return JsonSchemaValidator.isValid(jsonNode, schemaProvider.getSchema());
    }
}
