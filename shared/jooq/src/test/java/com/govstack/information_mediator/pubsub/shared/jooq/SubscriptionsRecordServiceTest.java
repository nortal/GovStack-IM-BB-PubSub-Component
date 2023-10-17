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

class SubscriptionsRecordServiceTest {
    private final JsonService jsonService = new JsonService(getObjectMapper());
    private final SubscriptionsRecordService subscriptionsRecordService = new SubscriptionsRecordService(jsonService);

    @ParameterizedTest
    @MethodSource("correctSubscriptionParameters")
    void shouldValidateCorrectJson(Map<String, Object> params) {
        // Given
        var json = JSONB.jsonb(new JSONObject(params).toString());

        // When
        assertThatNoException().isThrownBy(() -> subscriptionsRecordService.validateParameters(json));
    }

    @ParameterizedTest
    @MethodSource("incorrectSubscriptionParameters")
    void shouldThrowExceptionWhenJsonIsIncorrect(Map<String, Object> params) {
        // Given
        var json = JSONB.jsonb(new JSONObject(params).toString());

        // When
        assertThatThrownBy(() -> subscriptionsRecordService.validateParameters(json))
            .isInstanceOf(InternalErrorException.class);
    }

    private static Stream<Arguments> correctSubscriptionParameters() {
        return SubscriptionsParametersTestDataUtil.correctSubscriptionParameters();
    }

    private static Stream<Arguments> incorrectSubscriptionParameters() {
        return SubscriptionsParametersTestDataUtil.incorrectSubscriptionParameters();
    }
}
