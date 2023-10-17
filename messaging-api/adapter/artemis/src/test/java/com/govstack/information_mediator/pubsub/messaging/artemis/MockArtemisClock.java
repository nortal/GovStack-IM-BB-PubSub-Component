package com.govstack.information_mediator.pubsub.messaging.artemis;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

class MockArtemisClock extends Clock {

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
        // Since JMS is at millisecond accuracy, integration tests may fail
        // on Windows machines because Windows resolves to nanosecond accuracy
        // by default. Truncation will ensure compatibility across platforms.
        return now.truncatedTo(ChronoUnit.MILLIS);
    }

    public void setNow(Instant now) {
        this.now = now;
    }
}
