package com.govstack.information_mediator.pubsub.management.domain.usecase.subscription;


import com.govstack.information_mediator.pubsub.domain.entity.id.SubscriptionID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.MemberIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;

public interface TerminateSubscriptionUseCase {

    void execute(Request request);

    record Request(RoomIdentifier room, MemberIdentifier member, SubscriptionID subscriptionID) {
    }
}
