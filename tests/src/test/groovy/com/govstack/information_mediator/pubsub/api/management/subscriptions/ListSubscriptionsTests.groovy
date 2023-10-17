package com.govstack.information_mediator.pubsub.api.management.subscriptions

import com.govstack.information_mediator.pubsub.UseCase
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator
import com.govstack.information_mediator.pubsub.config.GenericSpecification
import com.govstack.information_mediator.pubsub.config.TestData
import com.govstack.information_mediator.pubsub.requests.ManagementApi
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.restassured.http.Header
import io.restassured.response.Response
import spock.lang.Tag

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.*
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.not

//TODO: Add pagingSortingParameters tests
@Tag("PubSub")
@Tag("API")
@Epic("Management-api")
@Feature("GET /rooms/{memberId}/{roomId}/subscriptions")
class ListSubscriptionsTests extends GenericSpecification {

    String uuid, managerId, roomId, eventTypeId, eventTypeVersionId
    String managerIdentifier, roomIdentifier, eventTypeIdentifier, subscriptionIdentifier

    def setup() {
        uuid = UUIDGenerator.randomUUID()
        (managerIdentifier, roomIdentifier, eventTypeIdentifier, subscriptionIdentifier) =
                [TestData.getXRoadManagerIdentifier(uuid), "room-$uuid", "EVENT-$uuid", TestData.getXRoadSubscriberIdentifier(uuid)]
        managerId = db.addManager(managerIdentifier)
        roomId = db.addRoom(managerId, roomIdentifier)
        eventTypeId = db.addEventType(roomId, eventTypeIdentifier)
        eventTypeVersionId = db.addEventTypeVersion(eventTypeId)
    }

    @UseCase("UC4.2")
    @Story("UC4.2 - Successful subscriptions listing")
    def "Given: a member with subscriptions in different statuses in a room; When: member lists their status=#status subscriptions; Then: listing of subscription succeeds and #returnedStatus subscriptions are returned"() {
        given: "a member with subscriptions in different statuses in a room"
        // PENDING
        String url1 = "/${UUIDGenerator.randomUUID()}"
        String subscriptionId1 = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier, TestData.getValidPushSubscriptionParameter(url1))
        // ACTIVE
        String url2 = "/${UUIDGenerator.randomUUID()}"
        String subscriptionId2 = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier, TestData.getValidPushSubscriptionParameter(url2))
        db.addSubscriptionStatus(subscriptionId2, ACTIVE)
        // TERMINATED
        String url3 = "/${UUIDGenerator.randomUUID()}"
        String subscriptionId3 = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier, TestData.getValidPushSubscriptionParameter(url3))
        db.addSubscriptionStatus(subscriptionId3, ACTIVE)
        db.addSubscriptionStatus(subscriptionId3, TERMINATED)
        // REJECTED
        String url4 = "/${UUIDGenerator.randomUUID()}"
        String subscriptionId4 = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier, TestData.getValidPushSubscriptionParameter(url4))
        db.addSubscriptionStatus(subscriptionId4, REJECTED)

        def subscriptions = [PENDING   : [id: subscriptionId1, url: url1],
                             ACTIVE    : [id: subscriptionId2, url: url2],
                             TERMINATED: [id: subscriptionId3, url: url3],
                             REJECTED  : [id: subscriptionId4, url: url4]]

        when: "member lists their subscriptions"
        Response response = ManagementApi.listSubscriptions(subscriptionIdentifier, roomIdentifier, status)

        then: "listing of subscription succeeds"
        response.then().statusCode(200)

        and: "correct subscriptions are returned"
        response
                .then()
                .header("Content-Type", "application/json")
                .body("subscriptions.size()", is(returnedSubscriptionsCount),
                        "pagingMetadata.items", is(returnedSubscriptionsCount),
                        "pagingMetadata.limit", is(25),
                        "pagingMetadata.offset", is(0),
                        "pagingMetadata.items", is(returnedSubscriptionsCount))

        if (status == null) {
            subscriptions.eachWithIndex { key, subscription, index ->
                response
                        .then()
                        .body("subscriptions[$index].id", is(subscription.id),
                                "subscriptions[$index].identifier", is(subscriptionIdentifier),
                                "subscriptions[$index].eventTypeIdentifier", is(eventTypeIdentifier),
                                "subscriptions[$index].method", is("PUSH"),
                                "subscriptions[$index].pushUrl", is(subscription.url),
                                "subscriptions[$index].status", is(key),
                                // TODO: Add actual timestamp assertion
                                "subscriptions[$index].createdAt", is(not(null)))
            }
        } else {
            response
                    .then()
                    .body("subscriptions[0].id", is(subscriptions[status]["id"]),
                            "subscriptions[0].identifier", is(subscriptionIdentifier),
                            "subscriptions[0].eventTypeIdentifier", is(eventTypeIdentifier),
                            "subscriptions[0].method", is("PUSH"),
                            "subscriptions[0].pushUrl", is(subscriptions[status]["url"]),
                            "subscriptions[0].status", is(status.toString()),
                            // TODO: Add actual timestamp assertion
                            "subscriptions[0].createdAt", is(not(null)))
        }

        where:
        status       || returnedStatus | returnedSubscriptionsCount
        "PENDING"    || "PENDING"      | 1
        "ACTIVE"     || "ACTIVE"       | 1
        "TERMINATED" || "TERMINATED"   | 1
        "REJECTED"   || "REJECTED"     | 1
        null         || "all"          | 4
    }

    @UseCase("UC5.1")
    @Story("UC5.1 - Successful subscriptions listing")
    def "Given: a member with multiple subscriptions in different rooms; When: member lists their subscriptions; Then: listing of subscription succeeds and correct data is returned"() {
        given: "a member with multiple subscriptions in different rooms"
        def expectedSubscriptions = ["room1": [], "room2": []]

        // 3 subscriptions for room 1 and event type 1
        String subscriptionId = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier, TestData.pullSubscriptionParameter)
        expectedSubscriptions["room1"].add(getExpectedSubscription(subscriptionId, subscriptionIdentifier, eventTypeIdentifier, "PULL",
                PENDING.toString()))
        String url1 = "/${UUIDGenerator.randomUUID()}"
        String subscription2Id = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier, TestData.getValidPushSubscriptionParameter(url1))
        expectedSubscriptions["room1"].add(getExpectedSubscription(subscription2Id, subscriptionIdentifier, eventTypeIdentifier, "PUSH",
                PENDING.toString(), url1))
        String url2 = "/${UUIDGenerator.randomUUID()}"
        String subscription3Id = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier, TestData.getValidPushSubscriptionParameter(url2))
        expectedSubscriptions["room1"].add(getExpectedSubscription(subscription3Id, subscriptionIdentifier, eventTypeIdentifier, "PUSH",
                PENDING.toString(), url2))

        // 1 subscription for room 1 and event type 2
        String eventType2Identifier = "2-$eventTypeIdentifier"
        String eventType2Id = db.addEventType(roomId, eventType2Identifier)
        db.addEventTypeVersion(eventType2Id)
        String subscription4Id = db.addSubscription(roomId, eventType2Id, subscriptionIdentifier, TestData.pullSubscriptionParameter)
        expectedSubscriptions["room1"].add(getExpectedSubscription(subscription4Id, subscriptionIdentifier, eventType2Identifier, "PULL",
                PENDING.toString()))

        // 1 subscription for room 2 and event type 3
        String room2Id = db.addRoom(managerId, "2-$roomIdentifier")
        String eventType3Identifier = "3-$eventTypeIdentifier"
        String eventType3Id = db.addEventType(room2Id, eventType3Identifier)
        db.addEventTypeVersion(eventType3Id)
        String subscription5Id = db.addSubscription(room2Id, eventType3Id, subscriptionIdentifier, TestData.pullSubscriptionParameter)
        expectedSubscriptions["room2"].add(getExpectedSubscription(subscription5Id, subscriptionIdentifier, eventType3Identifier, "PULL",
                PENDING.toString()))

        when: "member lists their subscriptions"
        Response response = ManagementApi.listSubscriptions(subscriptionIdentifier, roomIdentifier)

        then: "listing of subscription succeeds"
        response.then().statusCode(200)

        and: "correct subscriptions are returned"
        response
                .then()
                .header("Content-Type", "application/json")
                .body("subscriptions.size()", is(4),
                        "pagingMetadata.items", is(4),
                        "pagingMetadata.limit", is(25),
                        "pagingMetadata.offset", is(0),
                        "pagingMetadata.items", is(4))

        expectedSubscriptions["room1"].eachWithIndex { subscription, index ->
            response
                    .then()
                    .body("subscriptions[$index].id", is(subscription["id"]),
                            "subscriptions[$index].identifier", is(subscription["identifier"]),
                            "subscriptions[$index].eventTypeIdentifier", is(subscription["eventTypeIdentifier"]),
                            "subscriptions[$index].method", is(subscription["method"]),
                            "subscriptions[$index].status", is(subscription["status"]),
                            // TODO: Add actual timestamp assertion
                            "subscriptions[0].createdAt", is(not(null)))
            if (subscription["method"] == "PUSH") {
                response
                        .then()
                        .body("subscriptions[$index].pushUrl", is(subscription["pushUrl"]))
            }
        }
    }

    @Story("Successful subscriptions listing")
    def "Given: a member with no subscriptions in a room; When: member lists their ACTIVE subscriptions; Then: listing of subscription succeeds and empty list is returned"() {
        given: "a member with no subscription in a room"

        when: "member lists their subscriptions"
        Response response = ManagementApi.listSubscriptions(subscriptionIdentifier, roomIdentifier)

        then: "listing of subscription succeeds"
        response.then().statusCode(200)

        response
                .then()
                .header("Content-Type", "application/json")
                .body("subscriptions.size()", is(0),
                        "pagingMetadata.items", is(0),
                        "pagingMetadata.limit", is(25),
                        "pagingMetadata.offset", is(0),
                        "pagingMetadata.items", is(0))
    }

    @Story("Unsuccessful subscriptions listing")
    def "Given: a member with a subscription in a room; When: member lists their subscriptions with a problem '#problem'; Then: listing of subscription fails with #statusCode"() {
        given: "a member with a subscriptions status in a room;"
        db.addSubscription(roomId, eventTypeId, subscriptionIdentifier)

        when: "member lists their subscriptions with a problem"
        Header header = new Header("X-Road-Client", subscriptionIdentifier)
        switch (problem) {
            case "non-existent room" ->
                roomIdentifier = "non-existent"
            case "terminated room" ->
                db.terminateRoom(roomId)
            case "'X-Road-Client' header missing" ->
                header = null
            case "'X-Road-Client' header not matching 'memberId'" ->
                header = new Header("X-Road-Client", "non-existent")
        }
        Response response = ManagementApi.listSubscriptions(subscriptionIdentifier, roomIdentifier, null, header)

        then: "listing of subscription fails"
        response.then().statusCode(statusCode)
        switch (statusCode) {
            case 403 ->
                response.then().body("timestamp", is(not(null)),
                        "status", is(statusCode),
                        "error", is(message),
                        "path", is("/rooms/${URLEncoder.encode(subscriptionIdentifier, "UTF-8")}/$roomIdentifier/subscriptions".toString()))
            case 404 ->
                response.then().body("message", is(message))
        }

        where:
        problem                                          || statusCode | message
        "non-existent room"                              || 404        | "Room was not found"
        "terminated room"                                || 404        | "Room was not found"
        "'X-Road-Client' header missing"                 || 403        | "Forbidden"
        "'X-Road-Client' header not matching 'memberId'" || 403        | "Forbidden"
    }

    static Map getExpectedSubscription(String subscriptionId, String subscriptionIdentifier, String eventTypeIdentifier, String method, String status, String pushUrl = null) {
        Map map = ["id"                 : subscriptionId,
                   "identifier"         : subscriptionIdentifier,
                   "eventTypeIdentifier": eventTypeIdentifier,
                   "method"             : method,
                   "status"             : status]
        if (pushUrl != null) {
            map.put("pushUrl", pushUrl)
        }
        return map
    }

}
