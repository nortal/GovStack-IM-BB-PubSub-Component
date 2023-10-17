package com.govstack.information_mediator.pubsub.messaging.web.auth;

import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Optional;

import static com.govstack.information_mediator.pubsub.messaging.web.auth.RequestUtil.getLastHeader;
import static com.govstack.information_mediator.pubsub.messaging.web.auth.RequestUtil.getPathParameter;

@Provider
class XRoadRequestFilter implements ContainerRequestFilter {

    private static final String PATH_PARAM_MEMBER_ID = "memberId";
    private static final String HEADER_X_ROAD_CLIENT = "X-Road-Client";

    @Inject
    XRoadContext xRoadContext;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        var client = getXRoadClient(requestContext);
        client.ifPresent(xRoadContext::setClient);

        getMember(requestContext).ifPresent(member -> {
            var xRoadClient = client.orElseThrow(() -> new ForbiddenException("Missing X-Road-Client header"));
            if (!member.equals(xRoadClient)) {
                throw new ForbiddenException("X-Road-Client is not allowed to access the requested resource");
            }
        });
    }

    private Optional<String> getXRoadClient(ContainerRequestContext requestContext) {
        return getLastHeader(requestContext, HEADER_X_ROAD_CLIENT);
    }

    private Optional<String> getMember(ContainerRequestContext requestContext) {
        return getPathParameter(requestContext, PATH_PARAM_MEMBER_ID);
    }
}
