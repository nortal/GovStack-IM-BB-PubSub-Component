package com.govstack.information_mediator.pubsub.api.messaging

import com.govstack.information_mediator.pubsub.UseCase
import com.govstack.information_mediator.pubsub.Utils
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator
import com.govstack.information_mediator.pubsub.config.GenericSpecification
import com.govstack.information_mediator.pubsub.config.TestData
import com.govstack.information_mediator.pubsub.domain.entity.Subscription
import com.govstack.information_mediator.pubsub.requests.MessagingApi
import com.govstack.information_mediator.pubsub.requests.Wiremock
import groovy.sql.GroovyRowResult
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Issue
import io.qameta.allure.Story
import io.restassured.http.Header
import io.restassured.response.Response
import spock.lang.Tag

import java.sql.Timestamp

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*

@Tag("PubSub")
@Tag("API")
@Epic("Messaging-api")
@Feature("POST /rooms/{memberId}/{roomId}/publish")
class PublishTests extends GenericSpecification {

    String uuid, managerId, roomId, eventTypeId, eventTypeVersionId, publisherId
    String managerIdentifier, roomIdentifier, eventTypeIdentifier, publisherIdentifier, subscriptionIdentifier

    def setup() {
        uuid = UUIDGenerator.randomUUID()
        (managerIdentifier, roomIdentifier, eventTypeIdentifier, publisherIdentifier, subscriptionIdentifier) =
                [TestData.getXRoadManagerIdentifier(uuid), "room-$uuid", "EVENT-$uuid", TestData.getXRoadPublisherIdentifier(uuid),
                 TestData.getXRoadSubscriberIdentifier(uuid)]
        managerId = db.addManager(managerIdentifier)
        roomId = db.addRoom(managerId, roomIdentifier)
        eventTypeId = db.addEventType(roomId, eventTypeIdentifier)
        eventTypeVersionId = db.addEventTypeVersion(eventTypeId)
        publisherId = db.addPublisher(roomId, publisherIdentifier)
        db.addPublisherConstraint(publisherId, eventTypeId)
    }

    @UseCase("UC6.1")
    @Story("UC6.1 - Successful publish")
    def "Given: an event type with #subscriptionStatus; When: message is published; Then: publish succeeds and event is created and no message is published"() {
        given: "an event type with no active subscriptions"
        switch (subscriptionStatus) {
            case "a PENDING subscription" ->
                db.addSubscription(roomId, eventTypeId, subscriptionIdentifier)
            case "a TERMINATED subscription" ->
                {
                    String subscriptionId = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier)
                    db.changeSubscriptionStatus(subscriptionId, Subscription.Status.ACTIVE)
                    db.changeSubscriptionStatus(subscriptionId, Subscription.Status.TERMINATED)
                }
            case "a REJECTED subscription" ->
                {
                    String subscriptionId = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier)
                    db.changeSubscriptionStatus(subscriptionId, Subscription.Status.REJECTED)
                }
        }

        when: "message is published"
        Response response = MessagingApi.publishMessage(publisherIdentifier, roomIdentifier,
                TestData.getValidMessageData(eventTypeIdentifier))

        then: "publish succeeds"
        response.then().statusCode(200)

        and: "event is created"
        List<GroovyRowResult> events = db.getEvents(roomId, eventTypeVersionId, eventTypeId, publisherId)
        assertThat(events, hasSize(1))
        String eventId = events[0].id.toString()
        response.then().body("eventId", is(eventId))

        and: "no message is published"
        List<GroovyRowResult> publishedMessages = db.getPublishedMessages(eventId)
        assertThat(publishedMessages, hasSize(0))

        where:
        subscriptionStatus          | _
        "no subscriptions"          | _
        "a PENDING subscription"    | _
        "a TERMINATED subscription" | _
        "a REJECTED subscription"   | _
    }

    @UseCase("UC6.1")
    @Story("UC6.1 - Successful publish")
    def "Given: an event type with 1 active PULL subscription; When: message is published; Then: publish succeeds and 1 event is created, published"() {
        given: "an event type with 1 active PULL subscription"
        String subscriptionId = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier)
        db.changeSubscriptionStatus(subscriptionId, Subscription.Status.ACTIVE)

        when: "message is published"
        Response response = MessagingApi.publishMessage(publisherIdentifier, roomIdentifier,
                TestData.getValidMessageData(eventTypeIdentifier))

        then: "publish succeeds"
        response.then().statusCode(200)

        and: "1 event is created"
        List<GroovyRowResult> events = db.getEvents(roomId, eventTypeVersionId, eventTypeId, publisherId)
        assertThat(events, hasSize(1))
        GroovyRowResult event = events[0]
        String eventId = event.id.toString()
        response.then().body("eventId", is(eventId))
        assertThat(event["correlation_id"], not(anyOf(is(""), is(null))))
        assertThat(event["created_at"].class, is(Timestamp.class))

        and: "the event is published"
        List<GroovyRowResult> publishedMessages = db.getPublishedMessages(eventId, subscriptionId)
        assertThat(publishedMessages, hasSize(1))
        GroovyRowResult message = publishedMessages[0]
        assertThat(message["published_at"].class, is(Timestamp.class))
        assertThat(message["delivered_at"], is(null))
    }

    @Issue("M604017-173")
    @Tag("Wiremock")
    @UseCase("UC6.1")
    @UseCase("UC6.2")
    @Story("UC6.2 - Successful publish & push")
    def "Given: an event type with 1 active PUSH subscription with pushUrl response: #responseStatus; When: message is published; Then: publish succeeds and 1 event is created, published and delivered with correct data"() {
        given: "an event type with 1 active PUSH subscription"
        //set up callback url stub
        Map mapping = Wiremock.getCallbackMapping(uuid, responseStatus)
        Wiremock.postMapping(mapping)

        String subscriptionId = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier,
                TestData.getValidPushSubscriptionParameter(mapping.request.url))
        db.changeSubscriptionStatus(subscriptionId, Subscription.Status.ACTIVE)

        when: "message is published"
        Map publishedData = TestData.getValidMessageData(eventTypeIdentifier)
        Response response = MessagingApi.publishMessage(publisherIdentifier, roomIdentifier, publishedData)

        then: "publish succeeds"
        response.then().statusCode(200)

        and: "1 event is created"
        List<GroovyRowResult> events = db.getEvents(roomId, eventTypeVersionId, eventTypeId, publisherId)
        assertThat(events, hasSize(1))
        GroovyRowResult event = events[0]
        String eventId = event.id.toString()
        response.then().body("eventId", is(eventId))
        assertThat(event["correlation_id"], not(anyOf(is(""), is(null))))
        assertThat(event["created_at"].class, is(Timestamp.class))

        and: "the event is published"
        List<GroovyRowResult> publishedMessages = db.getPublishedMessages(eventId, subscriptionId)
        assertThat(publishedMessages, hasSize(1))
        GroovyRowResult message = publishedMessages[0]
        assertThat(message["published_at"].class, is(Timestamp.class))
        assertThat(message["delivered_at"], is(null))

        and: "the event is delivered"
        // check on our side
        Utils.waitUntil(() -> db.getDeliveredPublishedMessages(subscriptionId, eventId).size() == 1, 30_000)
        List<GroovyRowResult> deliveredMessages = db.getDeliveredPublishedMessages(subscriptionId, eventId)
        GroovyRowResult deliveredMessage = deliveredMessages[0]
        assertThat(deliveredMessage["published_at"], is(null))
        assertThat(deliveredMessage["delivered_at"].class, is(Timestamp.class))

        // check on the callback service side
        Wiremock.findRequests(mapping.request)
                .then()
                .body("requests.size()", is(1),
                        "requests[0].body", is(Utils.mapToJsonString(publishedData.content)),
                        "requests[0].headers.Accept", is("application/json"),
                        "requests[0].headers.Content-Type", is("application/json"))

        where:
        responseStatus | _
        200            | _
//        404            | _
//        500            | _
    }

    @Tag("Wiremock")
    @UseCase("UC6.1")
    @UseCase("UC6.2")
    @Story("UC6.2 - Successful publish & push")
    def "Given: an event type with 10 active PUSH subscriptions; When: message is published; Then: publish succeeds and 1 event is created, published and delivered to 10 subscribers"() {
        given: "an event type with 10 active PUSH subscription"
        //set up callback url stubs
        ArrayList subscriptionIds = []
        ArrayList mappings = []
        for (int nr in 1..10) {
            Map mapping = Wiremock.getCallbackMapping()
            Wiremock.postMapping(mapping)
            mappings.add(mapping)

            String subscriptionId = db.addSubscription(roomId, eventTypeId, TestData.getXRoadSubscriberIdentifier(UUIDGenerator.randomUUID().toString()),
                    TestData.getValidPushSubscriptionParameter(mapping.request.url))
            db.changeSubscriptionStatus(subscriptionId, Subscription.Status.ACTIVE)
            subscriptionIds.add(subscriptionId)
        }

        when: "message is published"
        Map publishedData = TestData.getValidMessageData(eventTypeIdentifier)
        Response response = MessagingApi.publishMessage(publisherIdentifier, roomIdentifier, publishedData)

        then: "publish succeeds"
        response.then().statusCode(200)

        and: "1 event is created"
        List<GroovyRowResult> events = db.getEvents(roomId, eventTypeVersionId, eventTypeId, publisherId)
        assertThat(events, hasSize(1))
        GroovyRowResult event = events[0]
        String eventId = event.id.toString()
        response.then().body("eventId", is(eventId))
        assertThat(event["correlation_id"], not(anyOf(is(""), is(null))))
        assertThat(event["created_at"].class, is(Timestamp.class))

        and: "event is published to 10 subscribers"
        for (subscriptionId in subscriptionIds) {
            List<GroovyRowResult> publishedMessages = db.getPublishedMessages(eventId, subscriptionId)
            assertThat(publishedMessages, hasSize(1))
            GroovyRowResult message = publishedMessages[0]
            assertThat(message["published_at"].class, is(Timestamp.class))
            assertThat(message["delivered_at"], is(null))
        }

        and: "the event is delivered to 10 subscribers"
        // check on our side
        for (subscriptionId in subscriptionIds) {
            Utils.waitUntil(() -> db.getDeliveredPublishedMessages(subscriptionId, eventId).size() == 1,
                    30_000, 1_000)
            List<GroovyRowResult> deliveredMessages = db.getDeliveredPublishedMessages(subscriptionId, eventId)
            GroovyRowResult deliveredMessage = deliveredMessages[0]
            assertThat(deliveredMessage["published_at"], is(null))
            assertThat(deliveredMessage["delivered_at"].class, is(Timestamp.class))
        }

        // check on the callback service side
        for (mapping in mappings) {
            Wiremock.findRequests(mapping.request)
                    .then()
                    .body("requests.size()", equalTo(1),
                            "requests[0].body", equalTo(Utils.mapToJsonString(publishedData.content)),
                            "requests[0].headers.Accept", is("application/json"),
                            "requests[0].headers.Content-Type", is("application/json"))
        }
    }

    @UseCase("UC6.1")
    @Story("UC6.1 - Unsuccessful publish")
    def "Given: an event type; When: a message with problem '#problem' is published; Then: publish fails with #errorCode: '#errorMessage' "() {
        given: "an event type"

        when: "message with a problem is published"
        Map data = TestData.getValidMessageData(eventTypeIdentifier)
        switch (problem) {
            case "non-existent publisher" ->
                publisherIdentifier = "non-existent"
            case "terminated publisher" ->
                db.terminatePublisher(publisherId)
            case "non-existent room" ->
                roomIdentifier = "non-existent"
            case "terminated room" ->
                db.terminateRoom(roomId)
            case "non-existent event type" ->
                data.eventType = "non-existent"
            case "terminated event type" ->
                db.terminateEventType(eventTypeId)
            case "event type publish not allowed for publisher" ->
                {
                    String eventType2Identifier = "EVENT2-$uuid"
                    data.eventType = eventType2Identifier
                    String eventType2Id = db.addEventType(roomId, eventType2Identifier)
                    eventTypeVersionId = db.addEventTypeVersion(eventType2Id)
                }
            case "invalid request data" ->
                data.content = ["payload2": "test"]
            case "request data matching previous json schema version" ->
                eventTypeVersionId = db.addEventTypeVersion(eventTypeId, TestData.jsonSchema2, 2)
            case "eventType missing from data" ->
                data.remove("eventType")
        }
        Response response = MessagingApi.publishMessage(publisherIdentifier, roomIdentifier, data)

        then: "publish fails"
        response.then()
                .statusCode(errorCode)
                .body("message", is(errorMessage),
                        "timestamp", not(null))

        and: "no events created"
        List<GroovyRowResult> events = db.getEvents(roomId, eventTypeVersionId, eventTypeId, publisherId)
        assertThat(events, hasSize(0))

        where:
        problem                                              || errorCode | errorMessage
        "non-existent publisher"                             || 404       | "Publisher was not found"
        "terminated publisher"                               || 404       | "Publisher was not found"
        "non-existent room"                                  || 404       | "Room was not found"
        "terminated room"                                    || 404       | "Room was not found"
        "non-existent event type"                            || 404       | "Event Type was not found"
        "terminated event type"                              || 404       | "Event Type was not found"
        "event type publish not allowed for publisher"       || 404       | "Event Type was not found"
        "invalid request data"                               || 400       | "The event payload is not compatible with the event type"
        "request data matching previous json schema version" || 400       | "The event payload is not compatible with the event type"
    }

    @UseCase("UC6.1")
    @Story("UC6.1 - Unsuccessful publish")
    def "Given: an event type; When: a message missing '#missingField'; Then: publish fails with 'Invalid data for publishing event'"() {
        given: "an event type"

        when: "message with a problem is published"
        Map data = TestData.getValidMessageData(eventTypeIdentifier)
        if (missingField = "both") {
            data.remove("eventType")
            data.remove("content")
        } else {
            data.remove(missingField)
        }
        Response response = MessagingApi.publishMessage(publisherIdentifier, roomIdentifier, data)

        then: "publish fails"
        response.then()
                .statusCode(400)
                .body("title", is("Constraint Violation"),
                        "status", is(400),
                        "violations[0].field", is("publishEventToRoom.arg2"),
                        "violations[0].message", is("Invalid data for publishing event"))

        and: "no events created"
        List<GroovyRowResult> events = db.getEvents(roomId, eventTypeVersionId, eventTypeId, publisherId)
        assertThat(events, hasSize(0))

        where:
        missingField | _
        "eventType"  | _
        "content"    | _
        "both"       | _
    }

    @UseCase("UC6.1")
    @Story("UC6.1 - Unsuccessful publish")
    def "Given: an event type; When: a message has a problem with 'X-Road-Client' header #xRoadHeader; Then: publish fails with 'Technical error'"() {
        given: "an event type"

        when: "message with a problem is published"
        Header header = (xRoadHeader == "missing") ? null : new Header("X-Road-Client", "non-existent")
        Response response = MessagingApi.publishMessage(publisherIdentifier, roomIdentifier, TestData.getValidMessageData(eventTypeIdentifier), header)

        then: "publish fails"
        response.then()
                .statusCode(500)
                .body("message", matchesPattern("Technical error: ([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"),
                        "timestamp", not(null))

        and: "no events created"
        List<GroovyRowResult> events = db.getEvents(roomId, eventTypeVersionId, eventTypeId, publisherId)
        assertThat(events, hasSize(0))

        where:
        xRoadHeader             | _
        "missing"               | _
        "not matching memberId" | _
    }
}
