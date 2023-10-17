package com.govstack.information_mediator.pubsub.management.jdbc;

import java.util.UUID;

interface OnManagerCascadeDeletionListener {

    void onManagerDeleted(UUID managerId);
}
