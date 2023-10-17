package com.govstack.information_mediator.pubsub.shared.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.RecordListener;
import org.jooq.impl.DSL;

@RequiredArgsConstructor
class JournalRecordListener implements RecordListener {
    private static final String JOURNAL_NAME = "journal";
    private static final Field<JSONB> JOURNAL_FIELD = DSL.field(JOURNAL_NAME, JSONB.class);

    private final JournalRecordService journalRecordService;

    @Override
    public void insertStart(RecordContext ctx) {
        Record record = ctx.record();
        if (hasJournalField(record)) {
            record.set(JOURNAL_FIELD, journalRecordService.newJournal());
        }
    }

    @Override
    public void updateStart(RecordContext ctx) {
        Record record = ctx.record();
        if (hasJournalField(record)) {
            JSONB journal = record.get(JOURNAL_FIELD);
            record.set(JOURNAL_FIELD, journalRecordService.updateJournal(journal));
        }
    }

    private boolean hasJournalField(Record record) {
        return record.field(JOURNAL_NAME, JSONB.class) != null;
    }
}
