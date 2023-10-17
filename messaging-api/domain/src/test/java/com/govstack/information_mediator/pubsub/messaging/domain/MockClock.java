package com.govstack.information_mediator.pubsub.messaging.domain;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class MockClock extends Clock {

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

    public void setNow(Instant now) {
        this.now = now;
    }
}
