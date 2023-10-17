package com.govstack.information_mediator.pubsub.management.domain.usecase.subscription;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.view.SubscriptionEventDeliveryView;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

public interface ListSubscriptionEventsDeliveriesUseCase {

    Response execute(Request request);

    @Value
    @RequiredArgsConstructor(staticName = "of")
    class Request {

        RoomData room;
        MemberData member;
        UUID subscriptionId;
        PageRequest pageRequest;
        Instant fromDate;
        Instant toDate;
        UUID eventId;
        String eventTypeIdentifier;
        String deliveryStatus;

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

    record Response (Page<SubscriptionEventDeliveryView> subscriptionEventDeliveries) {}
}
