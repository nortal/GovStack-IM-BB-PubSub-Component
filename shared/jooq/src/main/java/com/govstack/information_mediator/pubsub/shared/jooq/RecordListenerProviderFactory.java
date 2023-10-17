package com.govstack.information_mediator.pubsub.shared.jooq;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import org.jooq.RecordListenerProvider;
import org.jooq.impl.DefaultRecordListenerProvider;

import java.time.Clock;

public class RecordListenerProviderFactory {

    private RecordListenerProviderFactory() {
        throw new AssertionError("Static factory should not be instantiated");
    }

    public static RecordListenerProvider createIdRecordListener() {
        return new DefaultRecordListenerProvider(new IdRecordListener());
    }

    public static RecordListenerProvider createJournalRecordListener(AuditInformation auditInformation, Clock clock,
                                                                     JsonService jsonService) {
        var listener = new JournalRecordListener(new DefaultJournalRecordService(auditInformation, clock, jsonService));
        return new DefaultRecordListenerProvider(listener);
    }

    public static RecordListenerProvider createRoomsRecordListener(JsonService jsonService) {
        var listener = new RoomsRecordListener(new RoomsRecordService(jsonService));
        return new DefaultRecordListenerProvider(listener);
    }

    public static RecordListenerProvider createSubscriptionsRecordListener(JsonService jsonService) {
        var listener = new SubscriptionsRecordListener(new SubscriptionsRecordService(jsonService));
        return new DefaultRecordListenerProvider(listener);
    }
}
