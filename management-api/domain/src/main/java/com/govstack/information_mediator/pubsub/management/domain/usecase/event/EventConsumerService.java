package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import org.springframework.stereotype.Component;

@Component
class EventConsumerService implements EventConsumerUseCase {

    @Override
    public void execute(Request request) {
        System.out.println("Received event: " + request);
    }
}
