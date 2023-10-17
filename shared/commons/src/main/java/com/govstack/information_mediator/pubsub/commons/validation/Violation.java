package com.govstack.information_mediator.pubsub.commons.validation;

public class Violation {
    public static final String MANAGER_NOT_FOUND = "Manager was not found";
    public static final String MANAGER_ALREADY_EXISTS = "Manager already exists";
    public static final String SUBSCRIPTION_NOT_FOUND = "Subscription was not found";
    public static final String EVENT_NOT_FOUND = "Event was not found";
    public static final String EVENT_TYPE_NOT_FOUND = "Event Type was not found";
    public static final String EVENT_TYPE_ALREADY_EXISTS = "Event Type already exists";
    public static final String EVENT_TYPE_VERSION_SCHEMA_INCORRECT_FORMAT = "The event type version JSON schema format is not correct";
    public static final String EVENT_TYPE_VERSION_NOT_FOUND = "Event Type Version was not found";
    public static final String EVENT_TYPE_VERSION_DELETION_NOT_ALLOWED = "Deletion of event type version is not allowed";
    public static final String EVENT_TYPE_VERSION_ALREADY_EXISTS = "The version already exists for the event type";
    public static final String PUBLISHER_NOT_FOUND = "Publisher was not found";
    public static final String ROOM_NOT_FOUND = "Room was not found";
    public static final String ROOM_ALREADY_EXISTS = "Room already exists";
    public static final String ROOM_IDENTIFIER_MODIFICATION_NOT_ALLOWED = "Room identifier modification is not allowed";
    public static final String ROOM_MANAGER_MODIFICATION_NOT_ALLOWED = "Room manager modification is not allowed";
}
