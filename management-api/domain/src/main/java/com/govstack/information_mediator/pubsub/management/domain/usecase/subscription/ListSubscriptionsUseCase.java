package com.govstack.information_mediator.pubsub.management.domain.usecase.subscription;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.view.SubscriptionView;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

public interface ListSubscriptionsUseCase {

    Response execute(Request request);

    @Value
    @RequiredArgsConstructor(staticName = "of")
    class Request {

        RoomData room;
        MemberData member;
        Subscription.Status status;
        PageRequest pageRequest;

        @Value
        @RequiredArgsConstructor(staticName = "of")
        public static class RoomData {
            String identifier;
        }

        @Value
        @RequiredArgsConstructor(staticName = "of")
        public static class MemberData {
            String identifier;
        }
    }

    record Response (Page<SubscriptionView> subscriptions) {}
}
