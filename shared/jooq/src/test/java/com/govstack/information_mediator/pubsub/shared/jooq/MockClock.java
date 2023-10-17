package com.govstack.information_mediator.pubsub.shared.jooq;

import lombok.Setter;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Setter
class MockClock extends Clock {

    private Instant now = Instant.now();

    @Override
    public ZoneId getZone() {
        return ZoneId.of("Europe/Tallinn");
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return this;
    }

    @Override
    public Instant instant() {
        // Since PostgreSQL is at microsecond accuracy, integration tests may fail
        // on Windows machines because Windows resolves to nanosecond accuracy
        // by default. Truncation will ensure compatibility across platforms.
        return now.truncatedTo(ChronoUnit.MICROS);
    }
}
