package com.govstack.information_mediator.pubsub.shared.jooq;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.ExecuteType;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.RecordType;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;

import java.time.Instant;
import java.util.Map;

record MockRecordContext(Record record) implements RecordContext {

    @Override
    public ExecuteType type() {
        throw new UnsupportedOperationException("Method is not implemented!");
    }

    @Override
    public RecordType<?> recordType() {
        throw new UnsupportedOperationException("Method is not implemented!");
    }

    @Override
    public Record[] batchRecords() {
        throw new UnsupportedOperationException("Method is not implemented!");
    }

    @Override
    public Exception exception() {
        throw new UnsupportedOperationException("Method is not implemented!");
    }

    @Override
    public Instant creationTime() {
        throw new UnsupportedOperationException("Method is not implemented!");
    }

    @Override
    public Configuration configuration() {
        throw new UnsupportedOperationException("Method is not implemented!");
    }

    @Override
    public DSLContext dsl() {
        throw new UnsupportedOperationException("Method is not implemented!");
    }

    @Override
    public Settings settings() {
        throw new UnsupportedOperationException("Method is not implemented!");
    }

    @Override
    public SQLDialect dialect() {
        throw new UnsupportedOperationException("Method is not implemented!");
    }

    @Override
    public SQLDialect family() {
        throw new UnsupportedOperationException("Method is not implemented!");
    }

    @Override
    public Map<Object, Object> data() {
        throw new UnsupportedOperationException("Method is not implemented!");
    }

    @Override
    public Object data(Object key) {
        throw new UnsupportedOperationException("Method is not implemented!");
    }

    @Override
    public Object data(Object key, Object value) {
        throw new UnsupportedOperationException("Method is not implemented!");
    }
}
