package com.govstack.information_mediator.pubsub.messaging.web.auth;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class XRoadRequestFilterTest {

    @Mock
    private ContainerRequestContext requestContext;

    @Mock
    private UriInfo uriInfo;

    @Spy
    private XRoadContext xRoadContext;

    @InjectMocks
    private XRoadRequestFilter xRoadRequestFilter;

    @Test
    void shouldAccessResource() {
        // Given
        var pathParameters = new MultivaluedHashMap<String, String>();
        pathParameters.add("memberId", "EE/BUSINESS/123456789");
        when(uriInfo.getPathParameters()).thenReturn(pathParameters);
        when(requestContext.getUriInfo()).thenReturn(uriInfo);
        when(requestContext.getHeaders()).thenReturn(defaultHeaders());

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> xRoadRequestFilter.filter(requestContext)),
            () -> assertThat(xRoadContext.getClient(), equalTo("EE/BUSINESS/123456789"))
        );
    }

    @Test
    void shouldAccessResourceWhenMemberIdIsNotPartOfPathParameters() {
        // Given
        when(uriInfo.getPathParameters()).thenReturn(new MultivaluedHashMap<>());
        when(requestContext.getUriInfo()).thenReturn(uriInfo);
        when(requestContext.getHeaders()).thenReturn(defaultHeaders());

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> xRoadRequestFilter.filter(requestContext)),
            () -> assertThat(xRoadContext.getClient(), equalTo("EE/BUSINESS/123456789"))
        );
    }

    @Test
    void shouldThrowExceptionWhenHeaderAndPathVariableDoNotMatch() {
        // Given
        var pathParameters = new MultivaluedHashMap<String, String>();
        pathParameters.add("memberId", "EE/BUSINESS/1234");

        when(uriInfo.getPathParameters()).thenReturn(pathParameters);
        when(requestContext.getUriInfo()).thenReturn(uriInfo);
        when(requestContext.getHeaders()).thenReturn(defaultHeaders());

        // Then
        Assertions.assertAll(
            () -> assertThatException()
                .isThrownBy(() -> xRoadRequestFilter.filter(requestContext))
                .isInstanceOf(ForbiddenException.class)
                .withMessage("X-Road-Client is not allowed to access the requested resource"),
            () -> assertThat(xRoadContext.getClient(), equalTo("EE/BUSINESS/123456789"))
        );
    }

    @Test
    void shouldThrowExceptionWhenXRoadClientHeaderIsMissing() {
        // Given
        when(requestContext.getHeaders()).thenReturn(new MultivaluedHashMap<>());

        var pathParameters = new MultivaluedHashMap<String, String>();
        pathParameters.add("memberId", "EE/BUSINESS/1234");

        when(uriInfo.getPathParameters()).thenReturn(pathParameters);
        when(requestContext.getUriInfo()).thenReturn(uriInfo);

        // Then
        Assertions.assertAll(
            () -> assertThatException()
                .isThrownBy(() -> xRoadRequestFilter.filter(requestContext))
                .isInstanceOf(ForbiddenException.class)
                .withMessage("Missing X-Road-Client header"),
            () -> verifyNoInteractions(xRoadContext),
            () -> assertNull(xRoadContext.getClient())
        );
    }

    @Test
    void shouldAccessResourceThatDoNotRequireXRoadClientCheck() {
        // Given
        when(uriInfo.getPathParameters()).thenReturn(new MultivaluedHashMap<>());
        when(requestContext.getUriInfo()).thenReturn(uriInfo);
        when(requestContext.getHeaders()).thenReturn(new MultivaluedHashMap<>());

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> xRoadRequestFilter.filter(requestContext)),
            () -> verifyNoInteractions(xRoadContext)
        );
    }

    private MultivaluedMap<String, String > defaultHeaders() {
        var headers = new MultivaluedHashMap<String, String>();
        headers.add("X-Road-Client", "member123");
        headers.add("X-Road-Client", "EE/BUSINESS/123456789");
        return headers;
    }
}
