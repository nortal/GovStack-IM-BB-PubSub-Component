package com.govstack.information_mediator.pubsub.shared.jooq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.exception.InternalErrorException;
import lombok.RequiredArgsConstructor;
import org.jooq.JSONB;

import java.time.Clock;
import java.util.LinkedList;

import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.JournalAction.CREATED;
import static com.govstack.information_mediator.pubsub.shared.jooq.JournalEntry.JournalAction.MODIFIED;

@RequiredArgsConstructor
class DefaultJournalRecordService implements JournalRecordService {

    private final JournalSchemaValidator journalValidator = new JournalSchemaValidator();

    private final AuditInformation auditInformation;
    private final Clock clock;
    private final JsonService jsonService;

    public JSONB newJournal() {
        LinkedList<JournalEntry> journal = new LinkedList<>();
        journal.add(JournalEntry.builder().at(clock.instant()).by(user()).action(CREATED).build());
        return JSONB.jsonb(validate(jsonService.writeValueAsString(journal)));
    }

    public JSONB updateJournal(JSONB journalData) {
        LinkedList<JournalEntry> journal = jsonService.readValue(journalData.data(), new TypeReference<>() { });
        journal.add(JournalEntry.builder().at(clock.instant()).by(user()).action(MODIFIED).build());
        return JSONB.jsonb(validate(jsonService.writeValueAsString(journal)));
    }

    private String user() {
        return auditInformation.getName();
    }

    private String validate(String journal) {
        if(journalValidator.isValid(jsonService.readTree(journal))) {
            return journal;
        }
        throw new InternalErrorException("Invalid JSON journal");
    }
}
