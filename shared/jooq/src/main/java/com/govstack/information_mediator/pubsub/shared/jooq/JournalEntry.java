package com.govstack.information_mediator.pubsub.shared.jooq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntry {

    public static final String AT = "at";
    public static final String BY = "by";
    public static final String ACTION = "action";

    @JsonProperty(AT)
    private Instant at;

    @JsonProperty(BY)
    private String by;

    @JsonProperty(ACTION)
    private JournalAction action;

    public enum JournalAction {
        CREATED,
        MODIFIED
    }
}
