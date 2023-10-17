package com.govstack.information_mediator.pubsub.shared.jooq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.exception.InternalErrorException;
import org.assertj.core.api.Assertions;
import org.jooq.JSONB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import static com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory.getObjectMapper;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.JournalAction.CREATED;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.JournalAction.MODIFIED;
import static org.assertj.core.groups.Tuple.tuple;

class DefaultJournalRecordServiceTest {

    private final JsonService jsonService = new JsonService(getObjectMapper());

    private MockClock clock;
    private DefaultJournalRecordService defaultJournalRecordService;

    @BeforeEach
    void setup() {
        clock = new MockClock();
        defaultJournalRecordService = new DefaultJournalRecordService(new MockAuditInformation(), clock, jsonService);
    }

    @Test
    void shouldCreateNewJournal() {
        // Given
        Instant entryTime = clock.instant();

        // When
        JSONB journal = defaultJournalRecordService.newJournal();

        // Then
        Assertions.assertThat(jsonService.readValue(journal.data(), new TypeReference<LinkedList<JournalEntry>>() { }))
            .hasSize(1)
            .extracting(JournalEntry::getAt, JournalEntry::getBy, JournalEntry::getAction)
            .containsExactly(
                tuple(entryTime, "SYSTEM", CREATED)
            );
    }

    @Test
    void shouldUpdateExistingJournal() {
        // Given
        Instant entryTime1 = Instant.now();
        Instant entryTime2 = Instant.now();
        Instant entryTime3 = Instant.now();

        JSONB existingJournal = JSONB.jsonb(jsonService.writeValueAsString(List.of(
            JournalEntry.builder().at(entryTime1).by("Mario").action(CREATED).build(),
            JournalEntry.builder().at(entryTime2).by("Luigi").action(MODIFIED).build(),
            JournalEntry.builder().at(entryTime3).by("Bauzer").action(MODIFIED).build()
        )));

        Instant entryTime = clock.instant();

        // When
        JSONB journal = defaultJournalRecordService.updateJournal(existingJournal);

        // Then
        Assertions.assertThat(jsonService.readValue(journal.data(), new TypeReference<LinkedList<JournalEntry>>() { }))
            .hasSize(4)
            .extracting(JournalEntry::getAt, JournalEntry::getBy, JournalEntry::getAction)
            .containsExactly(
                tuple(entryTime1, "Mario", CREATED),
                tuple(entryTime2, "Luigi", MODIFIED),
                tuple(entryTime3, "Bauzer", MODIFIED),
                tuple(entryTime, "SYSTEM", MODIFIED)
            );
    }

    @Test
    void shouldFailWhenPreviousSavedJournalIsInvalid() {
        // Given
        JSONB existingBadJournal = JSONB.jsonb(jsonService.writeValueAsString(List.of(
            JournalEntry.builder().at(Instant.now()).action(CREATED).build(),
            JournalEntry.builder().at(Instant.now()).action(MODIFIED).build(),
            JournalEntry.builder().action(MODIFIED).build()
        )));

        // When
        Assertions.assertThatThrownBy(() -> defaultJournalRecordService.updateJournal(existingBadJournal))
            .isInstanceOf(InternalErrorException.class);
    }
}
