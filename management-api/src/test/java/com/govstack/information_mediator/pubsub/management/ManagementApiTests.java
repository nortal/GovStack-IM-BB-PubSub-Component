package com.govstack.information_mediator.pubsub.management;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.yaml")
@ContextConfiguration(classes = ManagementApiTests.ArtemisAdapterITestConfig.class)
class ManagementApiTests {

    @Test
    void contextLoads() { }

    @EnableAutoConfiguration
    @SpringBootConfiguration
    static class ArtemisAdapterITestConfig { }
}
