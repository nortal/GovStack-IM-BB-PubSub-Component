package com.govstack.information_mediator.pubsub.shared.jooq;

import com.fasterxml.jackson.databind.JsonNode;
import com.govstack.information_mediator.pubsub.commons.JsonService;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory.getObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

class RoomsConfigurationSchemaValidatorTest {
    private final RoomsConfigurationSchemaValidator configurationValidator = new RoomsConfigurationSchemaValidator();
    private final JsonService jsonService = new JsonService(getObjectMapper());

    @ParameterizedTest
    @MethodSource("correctRoomConfigurations")
    void shouldValidateCorrectJson(Map<String, Object> params) {
        // Given
        JsonNode json = jsonService.readTree(new JSONObject(params).toString());

        // When
        assertThat(configurationValidator.isValid(json)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("incorrectRoomConfigurations")
    void shouldValidateIncorrectJson(Map<String, Object> params) {
        // Given
        JsonNode json = jsonService.readTree(new JSONObject(params).toString());

        // When
        assertThat(configurationValidator.isValid(json)).isFalse();
    }

    private static Stream<Arguments> correctRoomConfigurations() {
        return RoomsConfigurationTestDataUtil.correctRoomConfigurations();
    }

    public static Stream<Arguments> incorrectRoomConfigurations() {
        return RoomsConfigurationTestDataUtil.incorrectRoomConfigurations();
    }
}
