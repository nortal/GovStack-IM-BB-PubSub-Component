package com.govstack.information_mediator.pubsub.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.regex.Pattern;

public class ObjectMapperFactory {
  private static final ObjectMapper OBJECT_MAPPER;
  private static final Pattern C0_AND_C1_PATTERN = Pattern.compile("[\\p{C}&&[^\\r\\n]]");

  static {
    OBJECT_MAPPER = new ObjectMapper();
    OBJECT_MAPPER.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
    OBJECT_MAPPER.registerModule(new JavaTimeModule());
    OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    SimpleModule module = new SimpleModule();
    module.addDeserializer(String.class, new ValidatingStringDeserializer());
    OBJECT_MAPPER.registerModule(module);
  }

  private ObjectMapperFactory() {
    throw new AssertionError("Static factory should not be instantiated");
  }

  public static ObjectMapper getObjectMapper() {
    return OBJECT_MAPPER;
  }

  static class ValidatingStringDeserializer extends StringDeserializer {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
      var value = super.deserialize(p, ctxt);
      if (C0_AND_C1_PATTERN.matcher(value).find()) {
        throw new JsonMappingException(p, "Contains forbidden characters");
      }
      return value;
    }
  }

}
