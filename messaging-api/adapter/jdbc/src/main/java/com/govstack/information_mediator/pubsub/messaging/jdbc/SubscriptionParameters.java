package com.govstack.information_mediator.pubsub.messaging.jdbc;

import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import lombok.Builder;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;

import static com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory.getObjectMapper;

@Builder
public record SubscriptionParameters(
    String method,
    String pushUrl,
    Integer deliveryDelay,
    Double deliveryDelayMultiplier,
    Integer deliveryAttempts) {

    public static SubscriptionParameters fromDomainEntity(Subscription subscription) {
        return SubscriptionParameters.builder()
            .method(subscription.getParameters().getMethod().name())
            .pushUrl(subscription.getParameters().getPushUrl())
            .deliveryDelay(subscription.getParameters().getDeliveryDelay())
            .deliveryDelayMultiplier(subscription.getParameters().getDeliveryDelayMultiplier())
            .deliveryAttempts(subscription.getParameters().getDeliveryAttempts())
            .build();
    }

    @SneakyThrows
    public static SubscriptionParameters fromJsonString(String json) {
        return getObjectMapper().readValue(json, SubscriptionParameters.class);
    }

    public Subscription.Parameters toDomainEntity() {
        return Subscription.Parameters.builder()
            .method(EnumUtils.getEnum(Subscription.Method.class, method))
            .pushUrl(pushUrl)
            .deliveryDelay(deliveryDelay)
            .deliveryDelayMultiplier(deliveryDelayMultiplier)
            .deliveryAttempts(deliveryAttempts)
            .build();
    }

    @SneakyThrows
    public String toJsonString() {
        return getObjectMapper().writeValueAsString(this);
    }
}
