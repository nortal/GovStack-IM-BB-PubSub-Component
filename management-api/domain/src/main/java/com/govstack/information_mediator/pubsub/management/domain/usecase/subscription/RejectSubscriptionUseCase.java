package com.govstack.information_mediator.pubsub.management.domain.usecase.subscription;

import com.govstack.information_mediator.pubsub.domain.entity.id.SubscriptionID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;

public interface RejectSubscriptionUseCase {

    void execute(Request request);

    record Request(
        RoomIdentifier room,
        ManagerIdentifier manager,
        SubscriptionID subscriptionID) {

    }
}
