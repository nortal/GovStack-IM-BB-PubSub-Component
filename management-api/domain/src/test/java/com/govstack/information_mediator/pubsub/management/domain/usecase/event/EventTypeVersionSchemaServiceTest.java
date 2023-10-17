package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory;
import com.govstack.information_mediator.pubsub.commons.exception.BusinessViolationException;
import com.govstack.information_mediator.pubsub.commons.validation.Violation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class EventTypeVersionSchemaServiceTest {

    @Test
    void shouldConvertMinimalSpecificationToJsonSchema() {
        var jsonService = new JsonService(ObjectMapperFactory.getObjectMapper());
        var service = new EventTypeVersionSchemaService(jsonService);

        var schemaString = "{\"type\":\"object\"}";
        var jsonSchema = service.convertToJsonSchema(schemaString);

        assertThat(jsonSchema.getSchemaNode(), equalTo(jsonService.readTree(schemaString)));
    }

    @Test
    void shouldConvertComplexSpecificationToJsonSchema() {
        var jsonService = new JsonService(ObjectMapperFactory.getObjectMapper());
        var service = new EventTypeVersionSchemaService(jsonService);

        var schemaString = """
            {
              "$schema": "https://json-schema.org/draft/2020-12/schema",
              "$id": "https://example.com/create-event-type-request-schema.json",
              "title": "Create event type request",
              "description": "Data for creating event types",
              "type": "object",
              "properties": {
                "identifier": {
                  "type": "string",
                  "description": "Describes the event type"
                },
                "versionNo": {
                  "type": "integer",
                  "description": "Identifies the version number"
                },
                "schema": {
                  "type": "string",
                  "description": "The version schema used for validating events"
                }
              },
              "required": [
                "identifier",
                "versionNo",
                "schema"
              ]
            }""";

        var jsonSchema = service.convertToJsonSchema(schemaString);

        assertThat(jsonSchema.getSchemaNode(), equalTo(jsonService.readTree(schemaString)));
    }

    @Test
    void shouldThrowExceptionWhenTypeIsNotObject() {
        var jsonService = new JsonService(ObjectMapperFactory.getObjectMapper());
        var service = new EventTypeVersionSchemaService(jsonService);

        var schemaString = "{\"type\":\"array\"}";

        assertThatThrownBy(() -> service.convertToJsonSchema(schemaString))
            .isInstanceOf(BusinessViolationException.class)
            .hasMessage(Violation.EVENT_TYPE_VERSION_SCHEMA_INCORRECT_FORMAT);
    }
}
