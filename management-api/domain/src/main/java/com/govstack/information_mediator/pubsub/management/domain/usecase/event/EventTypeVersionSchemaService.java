package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.exception.BusinessViolationException;
import com.govstack.information_mediator.pubsub.commons.schema.SchemaProvider;
import com.govstack.information_mediator.pubsub.commons.validation.Violation;
import com.networknt.schema.JsonSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class EventTypeVersionSchemaService {

    private final JsonService jsonService;

    public JsonSchema convertToJsonSchema(String schemaString) {
        try {
           if (isValid(schemaString)) {
               return new SchemaProvider(schemaString).getSchema();
           }
        } catch (Exception ignored) {

        }

        throw  new BusinessViolationException(Violation.EVENT_TYPE_VERSION_SCHEMA_INCORRECT_FORMAT);
    }

    private boolean isValid(String schemaString) {
        var schemaNode = jsonService.readTree(schemaString);
        var typeNode = schemaNode.get("type");
        return typeNode != null && "object".equals(typeNode.textValue());
    }
}
