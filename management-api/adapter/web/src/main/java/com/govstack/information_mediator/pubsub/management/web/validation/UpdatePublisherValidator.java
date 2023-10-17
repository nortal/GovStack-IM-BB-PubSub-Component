package com.govstack.information_mediator.pubsub.management.web.validation;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.schema.SchemaProvider;
import com.govstack.information_mediator.pubsub.commons.validation.JsonSchemaValidator;
import com.govstack.information_mediator.pubsub.management.web.publisher.UpdatePublisherDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class UpdatePublisherValidator implements ConstraintValidator<UpdatePublisherValidation, UpdatePublisherDTO> {

    private final SchemaProvider schemaProvider = new SchemaProvider(Path.of("update-publisher-schema.json"));

    private final JsonService jsonService;

    @Override
    public boolean isValid(UpdatePublisherDTO value, ConstraintValidatorContext context) {
        var jsonNode = jsonService.valueToTree(value);
        return JsonSchemaValidator.isValid(jsonNode, schemaProvider.getSchema());
    }
}
