package com.govstack.information_mediator.pubsub.management.jdbc;

import com.govstack.information_mediator.pubsub.domain.entity.Room;
import lombok.Builder;
import lombok.SneakyThrows;

import static com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory.getObjectMapper;

@Builder
record RoomConfiguration(
    Integer messageExpiration,
    Integer deliveryDelay,
    Double deliveryDelayMultiplier,
    Integer deliveryAttempts) {

    public static RoomConfiguration fromDomainEntity(Room room) {
        return RoomConfiguration.builder()
            .messageExpiration(room.getConfiguration().getMessageExpiration())
            .deliveryDelay(room.getConfiguration().getDeliveryDelay())
            .deliveryDelayMultiplier(room.getConfiguration().getDeliveryDelayMultiplier())
            .deliveryAttempts(room.getConfiguration().getDeliveryAttempts())
            .build();
    }

    @SneakyThrows
    public static RoomConfiguration fromJsonString(String json) {
        return getObjectMapper().readValue(json, RoomConfiguration.class);
    }

    public Room.Configuration toDomainEntity() {
        return Room.Configuration.builder()
            .messageExpiration(messageExpiration)
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
