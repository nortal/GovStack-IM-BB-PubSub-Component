package com.govstack.information_mediator.pubsub.shared.jooq;

import org.assertj.core.api.Assertions;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.govstack.information_mediator.pubsub.shared.jooq.JooqTestUtil.retrieveUpdatableRecord;

class IdRecordListenerTest {
    private static final Field<UUID> ID = DSL.field("id", UUID.class);

    private final IdRecordListener idRecordListener = new IdRecordListener();

    @Test
    void shouldSetRecordId() {
        retrieveUpdatableRecord()
            .stream()
            .map(JooqTestUtil::newInstance)
            .forEach(this::assertThatListenerSetsRecordId);
    }

    private void assertThatListenerSetsRecordId(Record record) {
        // Before
        Assertions.assertThat(record.get(ID)).isNull();

        // When
        RecordContext ctx = new MockRecordContext(record);
        idRecordListener.insertStart(ctx);

        // After
        Assertions.assertThat(record.get(ID)).isNotNull();
    }
}
