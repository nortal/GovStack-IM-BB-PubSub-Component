package com.govstack.information_mediator.pubsub.management.jdbc;

import java.util.UUID;

interface OnRoomsCascadeDeletionListener {

    void onRoomDeleted(UUID roomId);
}
