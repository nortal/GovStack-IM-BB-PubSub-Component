package com.govstack.information_mediator.pubsub.shared.jooq;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.exception.InternalErrorException;
import org.jooq.JSONB;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory.getObjectMapper;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoomsRecordServiceTest {
    private final RoomsRecordService roomsRecordService = new RoomsRecordService(new JsonService(getObjectMapper()));

    @ParameterizedTest
    @MethodSource("correctRoomConfigurations")
    void shouldValidateCorrectJson(Map<String, Object> params) {
        // Given
        var json = JSONB.jsonb(new JSONObject(params).toString());

        // When
        assertThatNoException().isThrownBy(() -> roomsRecordService.validateConfiguration(json));
    }

    @ParameterizedTest
    @MethodSource("incorrectRoomConfigurations")
    void shouldThrowExceptionWhenJsonIsIncorrect(Map<String, Object> params) {
        // Given
        var json = JSONB.jsonb(new JSONObject(params).toString());

        // When
        assertThatThrownBy(() -> roomsRecordService.validateConfiguration(json))
            .isInstanceOf(InternalErrorException.class);
    }

    private static Stream<Arguments> correctRoomConfigurations() {
        return RoomsConfigurationTestDataUtil.correctRoomConfigurations();
    }

    public static Stream<Arguments> incorrectRoomConfigurations() {
        return RoomsConfigurationTestDataUtil.incorrectRoomConfigurations();
    }
}
