package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.Manager;

public interface TerminateManagersPort {

    void terminate(Manager manager);
}
