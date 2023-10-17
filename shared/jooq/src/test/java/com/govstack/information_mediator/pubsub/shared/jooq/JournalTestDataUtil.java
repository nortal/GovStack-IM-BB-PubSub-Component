package com.govstack.information_mediator.pubsub.shared.jooq;

import org.json.JSONArray;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

final class JournalTestDataUtil {

    private JournalTestDataUtil() {
        throw new AssertionError("Static utility class should not be instantiated");
    }

    public static String jsonJournalWithOneEntry() {
        return new JSONArray().put(
            Map.of(
                "at", "2023-07-06T08:29:45.538976032Z",
                "by", "Mario",
                "action", "CREATED"
            )
        ).toString();
    }

    public static String jsonJournalWithTwoEntries() {
        return new JSONArray()
            .put(
                Map.of(
                    "at", "2023-07-06T08:29:45.538976032Z",
                    "by", "Mario",
                    "action", "CREATED"
                )
            ).put(
                Map.of(
                    "at", "2023-07-07T06:37:50.043323542Z",
                    "by", "Bauzer",
                    "action", "MODIFIED"
                )
            )
            .toString();
    }

    public static Stream<Arguments> correctJournalEntries() {
        return Stream.of(
            Arguments.of(
                List.of(
                    Map.of(
                        "at", "2023-07-06T08:29:45.538976032Z",
                        "by", "Mario",
                        "action", "CREATED"
                    ))),
            Arguments.of(
                List.of(
                    Map.of(
                        "at", "2023-07-06T08:29:45.538976032Z",
                        "by", "Mario",
                        "action", "CREATED"
                    ),
                    Map.of(
                        "at", "2023-07-07T08:29:45.538976032Z",
                        "by", "Mario",
                        "action", "MODIFIED"
                    )
                )
            )
        );
    }

    public static Stream<Arguments> incorrectJournalEntries() {
        return Stream.of(
            Arguments.of(emptyList()),
            Arguments.of(List.of(emptyMap())),
            Arguments.of(
                List.of(
                    Map.of(
                        "at", "Invalid-date",
                        "by", "Mario",
                        "action", "CREATED"
                    ))),
            Arguments.of(
                List.of(
                    Map.of(
                        "by", "Mario",
                        "action", "CREATED"
                    ))),
            Arguments.of(
                List.of(
                    Map.of(
                        "at", "2023-07-06T08:29:45.538976032Z",
                        "action", "CREATED"
                    ))),
            Arguments.of(
                List.of(
                    Map.of(
                        "at", "2023-07-06T08:29:45.538976032Z",
                        "by", "Mario",
                        "action", "UNDEFINED"
                    ))),
            Arguments.of(
                List.of(
                    Map.of(
                        "at", "2023-07-06T08:29:45.538976032Z",
                        "by", "Mario"
                    ))),
            Arguments.of(
                List.of(
                    Map.of(
                        "at", "2023-07-06T08:29:45.538976032Z",
                        "by", "Mario",
                        "action", "CREATED"
                    ),
                    Map.of(
                        "at", "2023-07-07T08:29:45.538976032Z",
                        "action", "MODIFIED"
                    )
                )
            )
        );
    }
}
