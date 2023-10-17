package com.govstack.information_mediator.pubsub.shared.jooq;

import com.fasterxml.jackson.databind.JsonNode;
import com.govstack.information_mediator.pubsub.commons.validation.JsonSchemaValidator;
import com.govstack.information_mediator.pubsub.commons.schema.SchemaProvider;

import java.nio.file.Path;

class JournalSchemaValidator {
    private final SchemaProvider schemaProvider = new SchemaProvider(Path.of("journal-schema.json"));

    public boolean isValid(JsonNode journal) {
        return JsonSchemaValidator.isValid(journal, schemaProvider.getSchema());
    }
}
