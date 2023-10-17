package com.govstack.information_mediator.pubsub.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Member {

    String identifier;
    String name;

    String subsystemCode;
    String memberCode;
    String memberClass;
    String xRoadInstance;

    public static Member fromIdentifier(String identifier, String name) {
        String[] parts = identifier.split(":");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid identifier format");
        }

        return Member.builder()
            .identifier(identifier)
            .name(name)
            .xRoadInstance(parts[0])
            .memberClass(parts[1])
            .memberCode(parts[2])
            .subsystemCode(parts[3])
            .build();
    }
}
