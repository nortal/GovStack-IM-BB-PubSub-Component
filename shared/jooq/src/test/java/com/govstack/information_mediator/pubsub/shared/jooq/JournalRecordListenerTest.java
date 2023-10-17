package com.govstack.information_mediator.pubsub.shared.jooq;

import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.impl.DSL;
import org.jooq.impl.UpdatableRecordImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.function.Supplier;

import static com.govstack.information_mediator.pubsub.shared.jooq.JooqTestUtil.retrieveUpdatableRecord;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalTestDataUtil.jsonJournalWithOneEntry;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalTestDataUtil.jsonJournalWithTwoEntries;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class JournalRecordListenerTest {
    private static final String JOURNAL_NAME = "journal";
    private static final Field<JSONB> JOURNAL_FIELD = DSL.field(JOURNAL_NAME, JSONB.class);

    @SuppressWarnings("rawtypes")
    private static final Collection<Class<? extends UpdatableRecordImpl>> RECORDS = retrieveUpdatableRecord();

    @Test
    void testJournalAuditOnInsertStart() {
        RECORDS.stream()
            .map(JooqTestUtil::newInstance)
            .forEach(record -> {
                if (record.field(JOURNAL_NAME, JSONB.class) != null) {
                    shouldCreateJournalWhenRecordIsAuditable(record);
                } else {
                    shouldNotCreateJournalWhenRecordIsNotAuditable(record);
                }
            });
    }

    private void shouldCreateJournalWhenRecordIsAuditable(Record record) {
        // Given
        RecordContext ctx = new MockRecordContext(record);
        String journal = jsonJournalWithOneEntry();

        MockJournalRecordService journalRecordService = new MockJournalRecordService();
        journalRecordService.setOnNewJournal(() -> JSONB.jsonb(journal));

        JournalRecordListener journalRecordListener = new JournalRecordListener(journalRecordService);

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> journalRecordListener.insertStart(ctx)),
            () -> assertThat(record.get(JOURNAL_FIELD)).matches((field) -> field.data().equals(journal)).as("Correct journal should be added")
        );
    }

    private void shouldNotCreateJournalWhenRecordIsNotAuditable(Record record) {
        // Given
        RecordContext ctx = new MockRecordContext(record);
        JournalRecordListener journalRecordListener = new JournalRecordListener(new MockJournalRecordService());

        // Then
        assertThatNoException().isThrownBy(() -> journalRecordListener.insertStart(ctx));
    }

    @Test
    void testJournalAuditOnUpdateStart() {
        RECORDS.stream()
            .map(JooqTestUtil::newInstance)
            .forEach(record -> {
                if (record.field(JOURNAL_NAME, JSONB.class) != null) {
                    shouldUpdateJournalWhenRecordIsAuditable(record);
                } else {
                    shouldNotUpdateJournalWhenRecordIsNotAuditable(record);
                }
            });
    }

    private void shouldUpdateJournalWhenRecordIsAuditable(Record record) {
        // Given
        RecordContext ctx = new MockRecordContext(record);
        record.set(JOURNAL_FIELD, JSONB.jsonb(jsonJournalWithOneEntry()));
        String updatedJournal = jsonJournalWithTwoEntries();

        MockJournalRecordService journalRecordService = new MockJournalRecordService();
        journalRecordService.setOnUpdateJournal(() -> JSONB.jsonb(updatedJournal));

        JournalRecordListener journalRecordListener = new JournalRecordListener(journalRecordService);

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> journalRecordListener.updateStart(ctx)),
            () -> assertThat(record.get(JOURNAL_FIELD)).matches((field) -> field.data().equals(updatedJournal)).as("Correct journal should be added")
        );
    }

    private void shouldNotUpdateJournalWhenRecordIsNotAuditable(Record record) {
        // Given
        RecordContext ctx = new MockRecordContext(record);
        JournalRecordListener journalRecordListener = new JournalRecordListener(new MockJournalRecordService());

        // Then
        assertThatNoException().isThrownBy(() -> journalRecordListener.updateStart(ctx));
    }

    private final static class MockJournalRecordService implements JournalRecordService {

        private Supplier<JSONB> onNewJournal = () -> {
            throw new IllegalStateException("New journal method should not have been invoked!");
        };

        private Supplier<JSONB> onUpdateJournal = () -> {
            throw new IllegalStateException("Update journal method should not have been invoked!");
        };

        @Override
        public JSONB newJournal() {
            return onNewJournal.get();
        }

        @Override
        public JSONB updateJournal(JSONB journalData) {
            return onUpdateJournal.get();
        }

        public void setOnNewJournal(Supplier<JSONB> onNewJournal) {
            this.onNewJournal = onNewJournal;
        }

        public void setOnUpdateJournal(Supplier<JSONB> onUpdateJournal) {
            this.onUpdateJournal = onUpdateJournal;
        }
    }
}
