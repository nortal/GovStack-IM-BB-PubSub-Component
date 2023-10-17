package com.govstack.information_mediator.pubsub.commons.util;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class UUIDGenerator {

    private UUIDGenerator() {
        throw new AssertionError("Static utility class should not be instantiated");
    }

    public static UUID randomUUID() {
        return Generators.timeBasedEpochGenerator().generate();
    }
}
