package com.govstack.information_mediator.pubsub.management.web.configuration;

import com.govstack.information_mediator.pubsub.management.web.keycloak.KeycloakJwtAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@PropertySource("classpath:management-api-oauth.properties")
@PropertySource("classpath:management-api.properties")
@RequiredArgsConstructor
class SecurityConfiguration {

    private final ApiRequestFilter apiRequestFilter;
    private final XRoadRequestFilter xRoadRequestFilter;
    @Value("${management.api.cors.allowed-origins}")
    private String[] allowedOrigins;
    @Value("${management.api.cors.allowed-methods}")
    private String[] allowedMethods;
    @Value("${management.api.cors.allowed-headers}")
    private String[] allowedHeaders;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/api/**").authenticated()
                        .requestMatchers("/rooms/**").permitAll()
                        .anyRequest().permitAll())
                .addFilterAfter(apiRequestFilter, AuthorizationFilter.class)
                .addFilterAfter(xRoadRequestFilter, AuthorizationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handler -> handler.authenticationEntryPoint(new HttpStatusEntryPoint(UNAUTHORIZED)))
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())))
                .cors(cors -> cors.configurationSource(request -> createCorsConfiguration()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .build();
    }

    private CorsConfiguration createCorsConfiguration() {
        var corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        corsConfiguration.setAllowedMethods(Arrays.asList(allowedMethods));
        corsConfiguration.setAllowedHeaders(Arrays.asList(allowedHeaders));
        return corsConfiguration;
    }

}
