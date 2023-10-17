package com.govstack.information_mediator.pubsub.domain.entity.identifier;

public class MemberIdentifier extends EntityIdentifier {
    public MemberIdentifier(String identifier) {
        super(identifier);
    }

    public ManagerIdentifier asManager() {
        return new ManagerIdentifier(getIdentifier());
    }

    public PublisherIdentifier asPublisher() {
        return new PublisherIdentifier(getIdentifier());
    }

    public SubscriptionIdentifier asSubscription() {
        return new SubscriptionIdentifier(getIdentifier());
    }
}
