package com.govstack.information_mediator.pubsub.management.domain.usecase.subscription;

import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateSubscriptionsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.CreateXRoadSubscriptionUseCase.Request.SubscriptionData;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PULL;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PUSH;

@Component
@RequiredArgsConstructor
public class CreateXRoadSubscriptionService implements CreateXRoadSubscriptionUseCase {

    private final FetchRoomsPort fetchRoomsPort;
    private final FetchEventTypesPort fetchEventTypesPort;
    private final CreateSubscriptionsPort createSubscriptionsPort;

    @Override
    @Transactional
    public Response execute(Request request) {
        var roomID = fetchRoom(request.getRoom());
        var eventTypeID = fetchEventType(request.getEventType(), roomID);
        return Response.of(createSubscription(request.getSubscription(), roomID, eventTypeID));
    }

    private RoomID fetchRoom(RoomIdentifier room) {
        return this.fetchRoomsPort.fetchRoomID(room).orElseThrow(RoomNotFoundException::new);
    }

    private EventTypeID fetchEventType(EventTypeIdentifier eventType, RoomID room) {
        return this.fetchEventTypesPort.fetchEventTypeID(eventType, room).orElseThrow(EventTypeNotFoundException::new);
    }

    private UUID createSubscription(SubscriptionData subscriptionData, RoomID roomID, EventTypeID eventTypeID) {
        var subscription = Subscription.builder()
            .roomId(roomID.getId())
            .eventTypeId(eventTypeID.getId())
            .identifierType(subscriptionData.getIdentifierType())
            .identifier(subscriptionData.getIdentifier())
            .parameters(mapSubscriptionParameters(subscriptionData.getParameters()))
            .status(EnumUtils.getEnum(Subscription.Status.class, subscriptionData.getStatus()))
            .build();
        return createSubscriptionsPort.createSubscription(subscription);
    }

    private Subscription.Parameters mapSubscriptionParameters(SubscriptionData.Parameters parameters) {
        var method = EnumUtils.getEnum(Subscription.Method.class, parameters.getMethod());
        if (method == PULL) {
            return Subscription.Parameters.builder()
                .method(method)
                .build();
        } else if (method == PUSH) {
            return Subscription.Parameters.builder()
                .method(method)
                .pushUrl(parameters.getPushUrl())
                .deliveryDelay(parameters.getDeliveryDelay())
                .deliveryDelayMultiplier(parameters.getDeliveryDelayMultiplier())
                .deliveryAttempts(parameters.getDeliveryAttempts())
                .build();
        }
        throw new ApiException("Creating subscription for method [" + method + "] is not allowed");
    }
}
