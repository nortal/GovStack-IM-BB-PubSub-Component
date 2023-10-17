package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.Manager;

import java.util.UUID;

public interface CreateManagersPort {

    UUID createManager(Manager manager);
}
