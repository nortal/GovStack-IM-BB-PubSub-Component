package com.govstack.information_mediator.pubsub.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.govstack.information_mediator.pubsub.commons.JsonService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;
import java.util.TimeZone;

import static com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory.getObjectMapper;

@Configuration
class GlobalConfiguration {

    @Value("${application.global.time-zone}")
    private String timeZone;

    @PostConstruct
    void setTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

    @Bean
    Clock clock() {
        return Clock.system(ZoneId.of(timeZone));
    }

    @Bean
    ObjectMapper objectMapper() {
        return getObjectMapper();
    }

    @Bean
    JsonService jsonService() {
        return new JsonService(objectMapper());
    }
}
