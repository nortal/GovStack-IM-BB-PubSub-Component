package com.govstack.information_mediator.pubsub.commons.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.govstack.information_mediator.pubsub.commons.exception.InternalErrorException;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Slf4j
@Getter
public class SchemaProvider {

  private static final JsonSchemaFactory SCHEMA_FACTORY = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
  private final JsonSchema schema;

  public SchemaProvider(Path schemaFilePath) {
    try (
      InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(schemaFilePath.toString());
    ) {
      schema = SCHEMA_FACTORY.getSchema(resourceStream);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new InternalErrorException("Error parsing schema file " + schemaFilePath);
    }
  }

  public SchemaProvider(String resource) {
    schema = SCHEMA_FACTORY.getSchema(resource);
  }

  public SchemaProvider(JsonNode jsonNode) {
      schema = SCHEMA_FACTORY.getSchema(jsonNode);
  }
}
