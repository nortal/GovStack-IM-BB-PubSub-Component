package com.govstack.information_mediator.pubsub.shared.jooq;

import com.fasterxml.jackson.databind.JsonNode;
import com.govstack.information_mediator.pubsub.commons.JsonService;
import org.json.JSONArray;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory.getObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

class JournalSchemaValidatorTest {
    private final JournalSchemaValidator journalValidator = new JournalSchemaValidator();
    private final JsonService jsonService = new JsonService(getObjectMapper());

    @ParameterizedTest
    @MethodSource("correctJournalEntries")
    void shouldValidateCorrectJson(List<Map<String, Object>> params) {
        // Given
        JSONArray array = new JSONArray();
        params.forEach(array::put);
        JsonNode json = jsonService.readTree(array.toString());

        // When
        assertThat(journalValidator.isValid(json)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("incorrectJournalEntries")
    void shouldValidateIncorrectJson(List<Map<String, Object>> params) {
        // Given
        JSONArray array = new JSONArray();
        params.forEach(array::put);
        JsonNode json = jsonService.readTree(array.toString());

        // When
        assertThat(journalValidator.isValid(json)).isFalse();
    }

    private static Stream<Arguments> correctJournalEntries() {
        return JournalTestDataUtil.correctJournalEntries();
    }

    private static Stream<Arguments> incorrectJournalEntries() {
        return JournalTestDataUtil.incorrectJournalEntries();
    }
}
