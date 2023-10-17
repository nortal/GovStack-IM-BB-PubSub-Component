package com.govstack.information_mediator.pubsub.shared.jooq;

import org.json.JSONObject;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Map;
import java.util.stream.Stream;

import static org.json.JSONObject.NULL;

final class RoomsConfigurationTestDataUtil {

    private RoomsConfigurationTestDataUtil() {
        throw new AssertionError("Static utility class should not be instantiated");
    }

    public static String jsonRoomConfiguration() {
        return new JSONObject(
            Map.of(
                "messageExpiration", 60 * 60 * 1000,
                "deliveryDelay", 5,
                "deliveryDelayMultiplier", 2,
                "deliveryAttempts", 5
            )
        ).toString();
    }

    public static Stream<Arguments> correctRoomConfigurations() {
        return Stream.of(
            Arguments.of(Map.of(
                "messageExpiration", 60 * 60 * 1000,
                "deliveryDelay", 5,
                "deliveryDelayMultiplier", 2,
                "deliveryAttempts", 5
            )),
            Arguments.of(Map.of(
                "messageExpiration", NULL,
                "deliveryDelay", 1,
                "deliveryDelayMultiplier", 1.01,
                "deliveryAttempts", 0
            ))
        );
    }

    public static Stream<Arguments> incorrectRoomConfigurations() {
        return Stream.of(
            Arguments.of(Map.of()),
            Arguments.of(Map.of(
                "messageExpiration", "PT10M",
                "deliveryDelay", "PT15S",
                "deliveryAttempts", 3
            )),
            Arguments.of(Map.of(
                "messageExpiration", 0.2,
                "deliveryDelay", 0.2,
                "deliveryDelayMultiplier", "text",
                "deliveryAttempts", -1
            )),
            Arguments.of(Map.of(
                "messageExpiration", "0.2",
                "deliveryDelay", "0.2",
                "deliveryDelayMultiplier", "2",
                "deliveryAttempts", "1"
            ))
        );
    }
}
