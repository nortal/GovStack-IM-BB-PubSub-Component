package com.govstack.information_mediator.pubsub.management.jdbc;

import java.util.UUID;

interface OnEventTypesCascadeDeletionListener {

    void onEventTypeDeleted(UUID eventTypeId);
}
