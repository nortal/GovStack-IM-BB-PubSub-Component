package com.govstack.information_mediator.pubsub.shared.jooq;

import org.jooq.JSONB;

interface JournalRecordService {

    JSONB newJournal();

    JSONB updateJournal(JSONB journalData);
}
