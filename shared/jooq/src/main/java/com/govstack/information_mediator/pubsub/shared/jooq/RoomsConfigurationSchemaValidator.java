package com.govstack.information_mediator.pubsub.shared.jooq;

import com.fasterxml.jackson.databind.JsonNode;
import com.govstack.information_mediator.pubsub.commons.validation.JsonSchemaValidator;
import com.govstack.information_mediator.pubsub.commons.schema.SchemaProvider;

import java.nio.file.Path;

class RoomsConfigurationSchemaValidator {
    private final SchemaProvider schemaProvider = new SchemaProvider(Path.of("rooms-configuration-schema.json"));

    public boolean isValid(JsonNode configuration) {
        return JsonSchemaValidator.isValid(configuration, schemaProvider.getSchema());
    }
}
