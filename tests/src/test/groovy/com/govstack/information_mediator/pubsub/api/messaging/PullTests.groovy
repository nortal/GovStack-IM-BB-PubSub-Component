package com.govstack.information_mediator.pubsub.api.messaging

import com.govstack.information_mediator.pubsub.UseCase
import com.govstack.information_mediator.pubsub.Utils
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator
import com.govstack.information_mediator.pubsub.config.GenericSpecification
import com.govstack.information_mediator.pubsub.config.TestData
import com.govstack.information_mediator.pubsub.domain.entity.Subscription
import com.govstack.information_mediator.pubsub.requests.MessagingApi
import groovy.sql.GroovyRowResult
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.restassured.http.Header
import io.restassured.response.Response
import spock.lang.Tag

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*

@Tag("PubSub")
@Tag("API")
@Epic("Messaging-api")
@Feature("GET /rooms/{memberId}/{roomId}/pull")
class PullTests extends GenericSpecification {

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

    @UseCase("UC6.3")
    @Story("UC6.3 - Successful pull")
    def "Given: event type with 1 active PULL subscription and a published message; When: message is pulled; Then: pull succeeds and no more events in queue and message marked as delivered"() {
        given: "event type with 1 active PULL subscription and a published message"
        String subscriptionId = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier)
        db.changeSubscriptionStatus(subscriptionId, Subscription.Status.ACTIVE)
        Map publishedData = TestData.getValidMessageData(eventTypeIdentifier)
        MessagingApi.publishMessage(publisherIdentifier, roomIdentifier, publishedData)
                .then().statusCode(200)

        when: "message is pulled"
        Response response = MessagingApi.pullMessage(subscriptionIdentifier, roomIdentifier, subscriptionId)

        then: "pull succeeds and no more events in queue"
        response.then()
                .statusCode(200)
                .header("Another-Event-In-Queue", is("false"))
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(is(Utils.mapToJsonString(publishedData.content)))

        and: "message marked as delivered"
        GroovyRowResult event = db.getEvents(roomId, eventTypeVersionId, eventTypeId, publisherId)[0]
        List<GroovyRowResult> events = db.getDeliveredPublishedMessages(subscriptionId, event["id"].toString())
        assertThat(events, hasSize(1))
    }

    @UseCase("UC6.3")
    @Story("UC6.3 - Successful pull")
    def "Given: 2 event types with 1 active PULL subscription and 3 published message across event types; When: messages are pulled successfully in a loop; Then: no more events in queue and messages marked as delivered"() {
        given: "2 event type with 1 active PULL subscription and 3 published message across event types"
        // 1.  event type, publish 2 messages
        String subscriptionId = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier)
        db.changeSubscriptionStatus(subscriptionId, Subscription.Status.ACTIVE)

        ArrayList<Map> eventType1PublishedData = []

        Map publishedData = TestData.getValidMessageData(eventTypeIdentifier)
        MessagingApi.publishMessage(publisherIdentifier, roomIdentifier, publishedData)
                .then().statusCode(200)
        eventType1PublishedData.add(publishedData)

        Map publishedData2 = TestData.getValidMessageData(eventTypeIdentifier, ["payload": "test2"])
        MessagingApi.publishMessage(publisherIdentifier, roomIdentifier, publishedData2)
                .then().statusCode(200)
        eventType1PublishedData.add(publishedData2)

        // 2. event type, publish 1 message
        String eventType2Identifier = "2-$eventTypeIdentifier"
        String eventType2Id = db.addEventType(roomId, eventType2Identifier)
        String eventType2VersionId = db.addEventTypeVersion(eventType2Id)
        db.addPublisherConstraint(publisherId, eventType2Id)

        String subscription2Id = db.addSubscription(roomId, eventType2Id, subscriptionIdentifier)
        db.changeSubscriptionStatus(subscription2Id, Subscription.Status.ACTIVE)

        Map publishedData3 = TestData.getValidMessageData(eventType2Identifier, ["payload": "test3"])
        MessagingApi.publishMessage(publisherIdentifier, roomIdentifier, publishedData3)
                .then().statusCode(200)

        when: "messages are pulled successfully in a loop"
        eventType1PublishedData.eachWithIndex { Map item, int index ->
            Response response = MessagingApi.pullMessage(subscriptionIdentifier, roomIdentifier, subscriptionId)
            response.then()
                    .statusCode(200)
                    .body(is(Utils.mapToJsonString(item.content)))
                    .header("Content-Type", "application/json;charset=UTF-8")
            String moreMessagesInQueue = (index < eventType1PublishedData.size() - 1) ? "true" : "false"
            response.then().header("Another-Event-In-Queue", is(moreMessagesInQueue))
        }

        then: "no more events in queue and messages marked as delivered"
        assertThat(db.getDeliveredPublishedMessages(subscriptionId), hasSize(2))
    }

    @UseCase("UC6.3")
    @Story("UC6.3 - Unsuccessful pull")
    def "Given: event type with 1 active PULL subscription; When: message is pulled with problem '#problem'; Then: pull fails with #statusCode"() {
        given: "event type with 1 active PULL subscription"
        String subscriptionId = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier)
        db.changeSubscriptionStatus(subscriptionId, Subscription.Status.ACTIVE)
        Map publishedData = TestData.getValidMessageData(eventTypeIdentifier)
        if (problem != "no published message") {
            MessagingApi.publishMessage(publisherIdentifier, roomIdentifier, publishedData)
                    .then().statusCode(200)
        }

        when: "message is pulled with a problem"
        Header header = new Header("X-Road-Client", subscriptionIdentifier)
        switch (problem) {
            case "non-existent subscription" ->
                {
                    subscriptionIdentifier = "non-existent"
                    header = new Header("X-Road-Client", subscriptionIdentifier)
                }
            case "TERMINATED subscription status" ->
                db.changeSubscriptionStatus(subscriptionId, Subscription.Status.TERMINATED)
            case "terminated subscription" ->
                db.terminateSubscription(subscriptionId)
            case "non-existent room" ->
                roomIdentifier = "non-existent"
            case "terminated room" ->
                db.terminateRoom(roomId)
            case "subscriptionId query parameter missing" ->
                subscriptionId = null
            case "subscriptionId query parameter not an uuid" ->
                subscriptionId = "test"
            case "'X-Road-Client' header missing" ->
                header = null
            case "'X-Road-Client' header not matching 'memberId'" ->
                header = new Header("X-Road-Client", "non-existent")
        }
        Response response = MessagingApi.pullMessage(subscriptionIdentifier, roomIdentifier, subscriptionId, header)

        then: "pull fails"
        if (statusCode == 404 && message != null) {
            response.then().body("message", is(message))
        }
        if (statusCode == 500) {
            response.then().body("message", matchesPattern(message))

        }

        where:
        problem                                          || statusCode | message
        "non-existent subscription"                      || 404 | "Subscription was not found"
        "TERMINATED subscription status"                 || 404 | "Subscription was not found"
        "terminated subscription"                        || 404 | "Subscription was not found"
        "non-existent room"                              || 404 | "Room was not found"
        "terminated room"                                || 404 | "Room was not found"
        "no published message"                           || 404 | null
        "subscriptionId query parameter missing"         || 404 | "Subscription was not found"
        "subscriptionId query parameter not an uuid"     || 500 | Utils.technicalErrorRegex
        "'X-Road-Client' header missing"                 || 500 | Utils.technicalErrorRegex
        "'X-Road-Client' header not matching 'memberId'" || 500 | Utils.technicalErrorRegex
    }

}
