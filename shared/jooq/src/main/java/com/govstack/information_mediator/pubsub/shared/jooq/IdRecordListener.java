package com.govstack.information_mediator.pubsub.shared.jooq;

import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import org.jooq.Field;
import org.jooq.RecordContext;
import org.jooq.RecordListener;
import org.jooq.impl.DSL;

import java.util.UUID;

class IdRecordListener implements RecordListener {

    private static final Field<UUID> ID = DSL.field("id", UUID.class);

    @Override
    public void insertStart(RecordContext ctx) {
        ctx.record().set(ID, UUIDGenerator.randomUUID());
    }
}
