package com.govstack.information_mediator.pubsub.management.web.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class XRoadPushUriBuilder {

    public String getFullPathForServiceEndpoint(String xRoadClient, String serviceEndpoint) {

        // Ensure serviceEndpoint does not start with a slash to avoid double slashes in the path
        if (serviceEndpoint != null && serviceEndpoint.startsWith("/")) {
            serviceEndpoint = serviceEndpoint.substring(1);
        }

        return String.format("%s/%s", xRoadClient, serviceEndpoint);
    }
}
