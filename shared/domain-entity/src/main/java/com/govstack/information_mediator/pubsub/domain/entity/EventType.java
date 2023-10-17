package com.govstack.information_mediator.pubsub.domain.entity;

import com.networknt.schema.JsonSchema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EventType {
    private UUID id;
    private UUID roomId;
    private String identifier;
    private Version version;

    @Data
    @Builder
    public static class Version {
        private UUID id;
        private Integer versionNo;
        private JsonSchema jsonSchema;
    }
}
