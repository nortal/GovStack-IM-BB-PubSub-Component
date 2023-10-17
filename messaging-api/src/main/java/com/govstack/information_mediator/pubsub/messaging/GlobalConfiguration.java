package com.govstack.information_mediator.pubsub.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.govstack.information_mediator.pubsub.commons.JsonService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Clock;
import java.time.ZoneId;
import java.util.TimeZone;

import static com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory.getObjectMapper;

@ApplicationScoped
class GlobalConfiguration {

    @ConfigProperty(name = "application.global.time-zone")
    String timeZone;

    @PostConstruct
    void setTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

    @Produces
    @ApplicationScoped
    Clock clock() {
        return Clock.system(ZoneId.of(timeZone));
    }

    @Produces
    @ApplicationScoped
    ObjectMapper objectMapper() {
        return getObjectMapper();
    }

    @Produces
    @ApplicationScoped
    JsonService jsonService() {
        return new JsonService(objectMapper());
    }
}
