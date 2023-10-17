package com.govstack.information_mediator.pubsub.shared.jooq;

import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.RoomsRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.RecordListener;
import org.jooq.impl.DSL;

@RequiredArgsConstructor
class RoomsRecordListener implements RecordListener {
    private static final Field<JSONB> CONFIGURATION_FIELD = DSL.field("configuration", JSONB.class);

    private final RoomsRecordService roomsRecordService;

    @Override
    public void insertStart(RecordContext ctx) {
        Record record = ctx.record();
        if (isRoomRecord(record)) {
            roomsRecordService.validateConfiguration(ctx.record().get(CONFIGURATION_FIELD));
        }
    }

    @Override
    public void updateStart(RecordContext ctx) {
        Record record = ctx.record();
        if (isRoomRecord(record)) {
            roomsRecordService.validateConfiguration(ctx.record().get(CONFIGURATION_FIELD));
        }
    }

    private static boolean isRoomRecord(Record record) {
        return record instanceof RoomsRecord;
    }
}
