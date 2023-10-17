package com.govstack.information_mediator.pubsub.messaging.domain.usecase.pushing;

import com.govstack.information_mediator.pubsub.messaging.domain.entity.PushEventMessage;

public interface PushEventMessagesUseCase {

    void execute(Request request);

    record Request(PushEventMessage eventMessage) {

    }
}
