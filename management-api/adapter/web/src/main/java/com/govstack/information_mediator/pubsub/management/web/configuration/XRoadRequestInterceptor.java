package com.govstack.information_mediator.pubsub.management.web.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Optional;

import static com.govstack.information_mediator.pubsub.management.web.configuration.RequestUtils.getXRoadClient;
import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;

class XRoadRequestInterceptor implements HandlerInterceptor {

    private static final String PATH_PARAM_MEMBER_ID = "memberId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var xRoadClient = getXRoadClient(request);

        if (xRoadClient.isEmpty()) {
            response.sendError(SC_FORBIDDEN);
            return false;
        }

        var isMemberXRoadClient = getMember(request)
            .map(member -> member.equals(xRoadClient.get()))
            .orElse(true);

        if (!isMemberXRoadClient) {
            response.sendError(SC_FORBIDDEN);
            return false;
        }

        return true;
    }

    private static Optional<String> getMember(HttpServletRequest request) {
        return getPathParameter(request, PATH_PARAM_MEMBER_ID);
    }

    private static Optional<String> getPathParameter(HttpServletRequest request, String parameter) {
        var pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return Optional.ofNullable((String) pathVariables.get(parameter));
    }
}
