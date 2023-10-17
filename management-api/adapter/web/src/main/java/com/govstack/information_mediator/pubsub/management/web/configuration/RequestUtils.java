package com.govstack.information_mediator.pubsub.management.web.configuration;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

final class RequestUtils {

    private static final String HEADER_X_ROAD_CLIENT = "X-Road-Client";

    private RequestUtils() {
        throw new AssertionError("Static utility class should not be instantiated");
    }

    static Optional<String> getXRoadClient(HttpServletRequest request) {
        return getLastHeader(request, HEADER_X_ROAD_CLIENT);
    }

    static Optional<String> getLastHeader(HttpServletRequest request, String header) {
        var values = request.getHeaders(header);
        String value = null;
        while (values.hasMoreElements()) {
            value = values.nextElement();
        }
        return Optional.ofNullable(value);
    }
}
