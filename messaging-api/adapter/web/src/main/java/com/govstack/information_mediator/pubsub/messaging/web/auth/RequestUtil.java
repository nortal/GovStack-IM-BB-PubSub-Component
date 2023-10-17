package com.govstack.information_mediator.pubsub.messaging.web.auth;

import jakarta.ws.rs.container.ContainerRequestContext;

import java.util.Optional;

final class RequestUtil {

    private RequestUtil() {
        throw new AssertionError("Static utility class should not be instantiated");
    }

    static Optional<String> getLastHeader(ContainerRequestContext requestContext, String header) {
        var values = requestContext.getHeaders().get(header);
        if (values == null || values.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(values.get(values.size() - 1));
    }

    static Optional<String> getPathParameter(ContainerRequestContext requestContext, String parameter) {
        return Optional.ofNullable(requestContext.getUriInfo().getPathParameters().getFirst(parameter));
    }

}
