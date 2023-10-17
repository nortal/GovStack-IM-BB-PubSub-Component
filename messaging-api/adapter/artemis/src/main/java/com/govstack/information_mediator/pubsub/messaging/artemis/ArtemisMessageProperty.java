package com.govstack.information_mediator.pubsub.messaging.artemis;

class ArtemisMessageProperty {
    public static final String EVENT_ID = "eventId";
    public static final String ROOM_ID = "roomId";
    public static final String EVENT_TYPE_ID = "eventTypeId";
    public static final String EVENT_TYPE_VERSION_ID = "eventTypeVersionId";
    public static final String PUBLISHER_ID = "publisherId";
    public static final String SUBSCRIPTION_ID = "subscriptionId";
    public static final String DELIVERY_ATTEMPT = "deliveryAttempt";

    public static final String CORRELATION_ID_PREFIX = "ID:";
    public static final String EMPTY_STRING = "";

    private ArtemisMessageProperty() {
        throw new AssertionError("Static class with artemis properties should not be instantiated");
    }
}
