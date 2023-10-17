package com.govstack.information_mediator.pubsub.shared.jooq;

import org.json.JSONObject;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Map;
import java.util.stream.Stream;

final class SubscriptionsParametersTestDataUtil {

    private SubscriptionsParametersTestDataUtil() {
        throw new AssertionError("Static utility class should not be instantiated");
    }

    public static String jsonPushConfiguration() {
        return new JSONObject(
            Map.of(
                "method", "PUSH",
                "pushUrl", "https://example.com/callback",
                "deliveryDelay", 1,
                "deliveryMultiplier", 0.01,
                "deliveryAttempts", 0
            )
        ).toString();
    }

    public static Stream<Arguments> correctSubscriptionParameters() {
        return Stream.of(
            Arguments.of(Map.of(
                "method", "PUSH",
                "pushUrl", "https://example.com/callback",
                "deliveryDelay", 1,
                "deliveryMultiplier", 0.01,
                "deliveryAttempts", 0
            )),
            Arguments.of(Map.of(
                "method", "PUSH",
                "pushUrl", "https://example.com/callback",
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "method", "PUSH",
                "pushUrl", "https://example.com/callback",
                "deliveryDelay", 2 * 1000
            )),
            Arguments.of(Map.of(
                "method", "PUSH",
                "pushUrl", "https://example.com/callback"
            )),
            Arguments.of(Map.of(
                "method", "PULL",
                "pushUrl", "https://example.com/callback",
                "deliveryDelay", 1,
                "deliveryMultiplier", 2,
                "deliveryAttempts", 0
            )),
            Arguments.of(Map.of(
                "method", "PULL",
                "deliveryDelay", 100,
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "method", "PULL",
                "pushUrl", "https://example.com/callback",
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "method", "PULL",
                "pushUrl", "https://example.com/callback",
                "deliveryDelay", 100
            )),
            Arguments.of(Map.of(
                "method", "PULL",
                "pushUrl", "https://example.com/callback"
            )),
            Arguments.of(Map.of(
                "method", "PULL",
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "method", "PULL",
                "deliveryDelay", 100
            )),
            Arguments.of(Map.of(
                "method", "PULL"
            ))
        );
    }

    public static Stream<Arguments> incorrectSubscriptionParameters() {
        return Stream.of(
            Arguments.of(Map.of()),
            Arguments.of(Map.of(
                "method", "PUSH"
            )),
            Arguments.of(Map.of(
                "method", "PUSH",
                "deliveryDelayMultiplier", 1,
                "deliveryDelay", 200,
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "method", "PUSH",
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "method", "PUSH",
                "deliveryDelay", 200
            )),
            Arguments.of(Map.of(
                "method", "PUSH",
                "pushUrl", "incorrect url",
                "deliveryDelay", 200,
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "method", "PUSH",
                "pushUrl", "https://example.com/callback",
                "deliveryDelay", "1000",
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "method", "PUSH",
                "pushUrl", "https://example.com/callback",
                "deliveryDelay", 200,
                "deliveryAttempts", "3"
            )),
            Arguments.of(Map.of(
                "pushUrl", "https://example.com/callback",
                "deliveryDelay", 200,
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "deliveryDelay", 200,
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "method", "PULL",
                "pushUrl", "incorrect url",
                "deliveryDelay", 200,
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "method", "PULL",
                "pushUrl", "https://example.com/callback",
                "deliveryDelay", 1000,
                "deliveryDelayMultiplier", 1,
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "method", "PULL",
                "deliveryDelay", 1000,
                "deliveryDelayMultiplier", "0.01",
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "method", "PULL",
                "pushUrl", "https://example.com/callback",
                "deliveryDelay", 200,
                "deliveryAttempts", "3"
            )),
            Arguments.of(Map.of(
                "method", "PULL",
                "deliveryDelay", 1000,
                "deliveryAttempts", "3"
            )),
            Arguments.of(Map.of(
                "method", "UNDEFINED_METHOD"
            ))
        );
    }
}
