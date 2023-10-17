package com.govstack.information_mediator.pubsub.shared.jooq;

import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.SubscriptionsRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.RecordListener;
import org.jooq.impl.DSL;

@RequiredArgsConstructor
class SubscriptionsRecordListener implements RecordListener {
    private static final Field<JSONB> PARAMETERS_FIELD = DSL.field("parameters", JSONB.class);

    private final SubscriptionsRecordService subscriptionsRecordService;

    @Override
    public void insertStart(RecordContext ctx) {
        Record record = ctx.record();
        if (isSubscriptionsRecord(record)) {
            subscriptionsRecordService.validateParameters(record.get(PARAMETERS_FIELD));
        }
    }

    @Override
    public void updateStart(RecordContext ctx) {
        Record record = ctx.record();
        if (isSubscriptionsRecord(record)) {
            subscriptionsRecordService.validateParameters(record.get(PARAMETERS_FIELD));
        }
    }

    private static boolean isSubscriptionsRecord(Record record) {
        return record instanceof SubscriptionsRecord;
    }
}
