package com.govstack.information_mediator.pubsub.management.web.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:management-api-actuator.properties")
public class ActuatorConfiguration { }
