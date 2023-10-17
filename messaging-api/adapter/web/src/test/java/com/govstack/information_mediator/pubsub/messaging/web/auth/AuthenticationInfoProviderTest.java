package com.govstack.information_mediator.pubsub.messaging.web.auth;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class AuthenticationInfoProviderTest {

    @Test
    void shouldReturnDefaultName() {
        // Given
        var authInfoProvider = new AuthenticationInfoProvider();

        // When
        assertThat(authInfoProvider.getName(), equalTo("MESSAGING-API"));
    }

    @Test
    void shouldReturnXRoadClient() {
        // Given
        var xRoadContext = new XRoadContext();
        xRoadContext.setClient("EE/BUSINESS/123456789");

        var authInfoProvider = new AuthenticationInfoProvider();
        authInfoProvider.xRoadContext = xRoadContext;

        // When
        assertThat(authInfoProvider.getName(), equalTo("EE/BUSINESS/123456789"));
    }
}
