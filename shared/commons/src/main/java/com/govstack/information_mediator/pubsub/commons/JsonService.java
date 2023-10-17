package com.govstack.information_mediator.pubsub.commons;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.govstack.information_mediator.pubsub.commons.exception.InternalErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JsonService {
    private final ObjectMapper objectMapper;

    public String writeValueAsString(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JacksonException e) {
            throw new InternalErrorException("Unable to serialize data as JSON string");
        }
    }

    public <T> T readValue(String data, TypeReference<T> type) {
        try {
            return objectMapper.readValue(data, type);
        } catch (JacksonException e) {
            throw new InternalErrorException("Unable to deserialize JSON data");
        }
    }

    public JsonNode readTree(String data) {
        try {
            return objectMapper.readTree(data);
        } catch (JacksonException e) {
            throw new InternalErrorException("Unable to read JSON tree for data");
        }
    }

    public JsonNode valueToTree(Object value) {
        try {
            return objectMapper.valueToTree(value);
        } catch (IllegalArgumentException e) {
            throw new InternalErrorException("Unable to convert value to JSON tree");
        }
    }
}
