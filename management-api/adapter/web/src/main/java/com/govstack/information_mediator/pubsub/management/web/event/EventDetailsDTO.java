package com.govstack.information_mediator.pubsub.management.web.event;

import com.govstack.information_mediator.pubsub.management.domain.entity.EventDetails;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.GetEventDetailsUseCase.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "EventDetails", description = "Event Details")
public class EventDetailsDTO {

    @Schema(description = "The event details", requiredMode = REQUIRED)
    private Event event;
    @Schema(description = "The event type details", requiredMode = REQUIRED)
    private EventType eventType;
    @Schema(description = "The event type version details", requiredMode = REQUIRED)
    private EventTypeVersion eventTypeVersion;
    @Schema(description = "The publisher details", requiredMode = REQUIRED)
    private Publisher publisher;
    @Schema(description = "The subscription details", requiredMode = REQUIRED)
    private List<Subscription> subscriptions;

    public static EventDetailsDTO fromResponse(Response response) {
        return EventDetailsDTO.builder()
            .event(Event.fromDomain(response.eventDetails().getEvent()))
            .eventType(EventType.fromDomain(response.eventDetails().getEventType()))
            .eventTypeVersion(EventTypeVersion.fromDomain(response.eventDetails().getEventTypeVersion()))
            .publisher(Publisher.fromDomain(response.eventDetails().getPublisher()))
            .subscriptions(response.eventDetails().getSubscriptions().stream().map(Subscription::fromDomain).toList())
            .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Event {
        @Schema(description = "The event ID", requiredMode = REQUIRED)
        UUID id;
        @Schema(description = "The event correlation ID", requiredMode = REQUIRED)
        String correlationId;
        @Schema(description = "Identifies when the event was created", requiredMode = REQUIRED)
        Instant createdAt;

        public static Event fromDomain(EventDetails.Event event) {
            return Event.builder()
                .id(event.getId())
                .correlationId(event.getCorrelationId())
                .createdAt(event.getCreatedAt())
                .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventType {
        @Schema(description = "The event type ID", requiredMode = REQUIRED)
        UUID id;
        @Schema(description = "The event type identifier", requiredMode = REQUIRED)
        String identifier;
        @Schema(description = "Identifies when the event type was deleted (from when it's inactive and no longer used)")
        Instant deletedAt;

        public static EventType fromDomain(EventDetails.EventType eventType) {
            return EventType.builder()
                .id(eventType.getId())
                .identifier(eventType.getIdentifier())
                .deletedAt(eventType.getDeletedAt())
                .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventTypeVersion {
        @Schema(description = "The event type version ID", requiredMode = REQUIRED)
        UUID id;
        @Schema(description = "Identifies the version of the event type", requiredMode = REQUIRED)
        Integer versionNo;
        @Schema(description = "Identifies the schema use for validating the event payload", requiredMode = REQUIRED)
        String schema;
        @Schema(description = "Identifies when the event type version was deleted (from when it's inactive and no longer used)")
        Instant deletedAt;

        public static EventTypeVersion fromDomain(EventDetails.EventTypeVersion eventTypeVersion) {
            return EventTypeVersion.builder()
                .id(eventTypeVersion.getId())
                .versionNo(eventTypeVersion.getVersionNo())
                .schema(eventTypeVersion.getSchema())
                .deletedAt(eventTypeVersion.getDeletedAt())
                .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Publisher {
        @Schema(description = "The publisher ID", requiredMode = REQUIRED)
        UUID id;
        @Schema(description = "The publisher identifier", requiredMode = REQUIRED)
        String identifier;
        @Schema(description = "The publisher identifier type", requiredMode = REQUIRED)
        String identifierType;
        @Schema(description = "Identifies when the publisher was deleted (from when it's inactive and no longer used)")
        Instant deletedAt;

        public static Publisher fromDomain(EventDetails.Publisher publisher) {
            return Publisher.builder()
                .id(publisher.getId())
                .identifier(publisher.getIdentifier())
                .identifierType(publisher.getIdentifierType().name())
                .deletedAt(publisher.getDeletedAt())
                .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Subscription {
        @Schema(description = "The subscription ID", requiredMode = REQUIRED)
        UUID id;
        @Schema(description = "The subscription identifier", requiredMode = REQUIRED)
        String identifier;
        @Schema(description = "The subscription identifier type", requiredMode = REQUIRED)
        String identifierType;
        @Schema(description = "The subscription status", requiredMode = REQUIRED)
        String status;
        @Schema(description = "Identifies if the event is delivered to the subscription", requiredMode = REQUIRED)
        Boolean isDelivered;
        @Schema(description = "Identifies the total number of delivery attempts", requiredMode = REQUIRED)
        Integer deliveryAttempts;
        @Schema(description = "Identifies the delivery attempts history (delivery attempts and successful deliveries)", requiredMode = REQUIRED)
        List<EventDelivery> deliveries;
        @Schema(description = "Identifies when the subscription was deleted (from when it's inactive and no longer used)", requiredMode = REQUIRED)
        Instant deletedAt;

        public static Subscription fromDomain(EventDetails.Subscription subscription) {
            return Subscription.builder()
                .id(subscription.getId())
                .identifier(subscription.getIdentifier())
                .identifierType(subscription.getIdentifierType().name())
                .status(subscription.getStatus().name())
                .deliveries(subscription.getDeliveries().stream().map(EventDelivery::fromDomain).toList())
                .deliveryAttempts(subscription.getDeliveryAttempts())
                .isDelivered(subscription.getIsDelivered())
                .deletedAt(subscription.getDeletedAt())
                .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventDelivery {
        @Schema(description = "Identifies the date and time of delivery attempt", requiredMode = REQUIRED)
        Instant publishedAt;
        @Schema(description = "Identifies the date and time of successful delivery", requiredMode = REQUIRED)
        Instant deliveredAt;

        public static EventDelivery fromDomain(EventDetails.EventDelivery eventDelivery) {
            return EventDelivery.builder()
                .publishedAt(eventDelivery.getPublishedAt())
                .deliveredAt(eventDelivery.getDeliveredAt())
                .build();
        }
    }
}
