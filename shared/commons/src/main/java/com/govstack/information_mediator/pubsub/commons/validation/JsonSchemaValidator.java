package com.govstack.information_mediator.pubsub.commons.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;

@Slf4j
public final class JsonSchemaValidator {

    private JsonSchemaValidator() {
        throw new AssertionError("Static json schema validator class should not be instantiated");
    }

    public static boolean isValid(JsonNode jsonNode, JsonSchema schema) {
        var errors = new LinkedHashSet<>(schema.validate(jsonNode));
        if (log.isDebugEnabled()) {
            errors.forEach(error -> log.debug("Schema validation error: {}", error));
        }
        return errors.isEmpty();
    }
}
