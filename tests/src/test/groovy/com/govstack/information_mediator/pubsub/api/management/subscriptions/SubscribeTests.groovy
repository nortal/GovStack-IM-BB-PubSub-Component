package com.govstack.information_mediator.pubsub.api.management.subscriptions

import com.govstack.information_mediator.pubsub.UseCase
import com.govstack.information_mediator.pubsub.Utils
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator
import com.govstack.information_mediator.pubsub.config.GenericSpecification
import com.govstack.information_mediator.pubsub.config.TestData
import com.govstack.information_mediator.pubsub.requests.ManagementApi
import groovy.sql.GroovyRowResult
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.restassured.http.Header
import io.restassured.response.Response
import spock.lang.Shared
import spock.lang.Tag

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*

@Tag("PubSub")
@Tag("API")
@Epic("Management-api")
@Feature("POST /rooms/{memberId}/{roomId}/subscriptions")
class SubscribeTests extends GenericSpecification {

    String uuid, managerId, roomId, eventTypeId, eventTypeVersionId
    @Shared
    String managerIdentifier, roomIdentifier, eventTypeIdentifier, subscriptionIdentifier

    def setup() {
        uuid = UUIDGenerator.randomUUID()
        (managerIdentifier, roomIdentifier, eventTypeIdentifier, subscriptionIdentifier) =
                [TestData.getXRoadManagerIdentifier(uuid), "room-$uuid", "EVENT-$uuid", "subscription-$uuid"]
        managerId = db.addManager(managerIdentifier)
        roomId = db.addRoom(managerId, roomIdentifier)
        eventTypeId = db.addEventType(roomId, eventTypeIdentifier)
        eventTypeVersionId = db.addEventTypeVersion(eventTypeId)
    }

    @UseCase("UC4.1")
    @Story("UC4.1 - Successful subscribe")
    def "Given: an active room with event type; When: member subscribes with data: #sentData; Then: subscribe succeeds and correct data is saved"() {
        given: "an active room with event type"

        when: "member subscribes with data"
        Map dataWithEventType = TestData.addEventType(sentData, eventTypeIdentifier)
        Response response = ManagementApi.subscribe(subscriptionIdentifier, roomIdentifier, dataWithEventType)

        then: "subscribe succeeds"
        response.then().statusCode(200)

        and: "correct data is saved"
        String subscriptionId = response.path("subscriptionId")
        GroovyRowResult subscriptionFromDb = db.getSubscription(subscriptionId)[0]
        assertThat(subscriptionFromDb["room_id"].toString(), is(roomId))
        assertThat(subscriptionFromDb["event_type_id"].toString(), is(eventTypeId))
        assertThat(subscriptionFromDb["identifier_type"], is("XROAD"))
        assertThat(subscriptionFromDb["identifier"], is(subscriptionIdentifier))
        assertThat(Utils.objectToMap(subscriptionFromDb["parameters"]), is(savedData))
        def journals = Utils.objectToMap(subscriptionFromDb["journal"])
        assertThat(journals, hasSize(1))
        def journal = journals[0]
        assertThat(journal["at"], is(not(null)))
        assertThat(journal["by"].toString(), is(subscriptionIdentifier))
        assertThat(journal["action"], is("CREATED"))
        assertThat(subscriptionFromDb["deleted_at"], is(null))

        where:
        sentData                                        | savedData
        ["method": "PULL"]                              | sentData

        ["method" : "PULL",
         "pushUrl": "/app/url"]                 | ["method": "PULL"]

        ["method"       : "PULL",
         "deliveryDelay": 100]                          | ["method": "PULL"]

        ["method"                 : "PULL",
         "deliveryDelayMultiplier": 10.0]               | ["method": "PULL"]

        ["method"          : "PULL",
         "deliveryAttempts": 100]                       | ["method": "PULL"]

        ["method" : "PUSH",
         "pushUrl":  "/app/url"]                 | ["method" : "PUSH",
                                                   "pushUrl": subscriptionId + "/app/url"]

        ["method"       : "PUSH",
         "pushUrl"      : "/app/url",
         "deliveryDelay": 100]                          | ["method"       : "PUSH",
                                                           "pushUrl"      : subscriptionId + "/app/url",
                                                           "deliveryDelay": 100]

        ["method"                 : "PUSH",
         "pushUrl"                : "/app/url",
         "deliveryDelayMultiplier": 10.0]               | ["method"                 : "PUSH",
                                                           "pushUrl"                : subscriptionId + "/app/url",
                                                           "deliveryDelayMultiplier": 10.0]

        ["method"          : "PUSH",
         "pushUrl"         : "/app/url",
         "deliveryAttempts": 100]                       | ["method"          : "PUSH",
                                                           "pushUrl"         : subscriptionId + "/app/url",
                                                           "deliveryAttempts": 100]

        ["method"                 : "PUSH",
         "pushUrl"                : "/app/url",
         "deliveryDelay"          : 100,
         "deliveryDelayMultiplier": 10.0,
         "deliveryAttempts"       : 100]                | ["method"                 : "PUSH",
                                                           "pushUrl"                : subscriptionId + "/app/url",
                                                           "deliveryDelay"          : 100,
                                                           "deliveryDelayMultiplier": 10.0,
                                                           "deliveryAttempts"       : 100]
    }

    @UseCase("UC4.1")
    @Story("UC4.1 - Successful subscribe")
    def "Given: an active room with event type and an active PUSH subscription; When: member subscribes 2nd time with a different pushUrl; Then: subscribe succeeds and correct data is saved"() {
        given: "an active room with event type and an active PUSH subscription"
        Map dataWithEventType = TestData.addEventType(TestData.getValidPushSubscriptionParameter(), eventTypeIdentifier)
        ManagementApi.subscribe(subscriptionIdentifier, roomIdentifier, dataWithEventType).then().statusCode(200)

        when: "member subscribes 2nd time with a different pushUrl"
        Map data = TestData.getValidPushSubscriptionParameter()
        dataWithEventType = TestData.addEventType(data, eventTypeIdentifier)
        Response response = ManagementApi.subscribe(subscriptionIdentifier, roomIdentifier, dataWithEventType)
        Map savedData = data
        savedData["pushUrl"] = subscriptionIdentifier + savedData["pushUrl"]

        then: "subscribe succeeds"
        response.then().statusCode(200)

        and: "correct data is saved"
        String subscriptionId = response.path("subscriptionId")
        GroovyRowResult subscriptionFromDb = db.getSubscription(subscriptionId)[0]
        assertThat(subscriptionFromDb["room_id"].toString(), is(roomId))
        assertThat(subscriptionFromDb["event_type_id"].toString(), is(eventTypeId))
        assertThat(subscriptionFromDb["identifier_type"], is("XROAD"))
        assertThat(subscriptionFromDb["identifier"], is(subscriptionIdentifier))
        assertThat(Utils.objectToMap(subscriptionFromDb["parameters"]), is(savedData))
        def journals = Utils.objectToMap(subscriptionFromDb["journal"])
        assertThat(journals, hasSize(1))
        def journal = journals[0]
        assertThat(journal["at"], is(not(null)))
        assertThat(journal["by"].toString(), is(subscriptionIdentifier))
        assertThat(journal["action"], is("CREATED"))
        assertThat(subscriptionFromDb["deleted_at"], is(null))
    }

    @UseCase("UC4.1")
    @Story("UC4.1 - Unsuccessful subscribe")
    def "Given: an active room with event type; When: member subscribes with problem: #problem; Then: subscribe fails with #statusCode"() {
        given: "an active room with event type"

        when: "member subscribes with a problem"
        Map data = TestData.addEventType(TestData.pullSubscriptionParameter, eventTypeIdentifier)
        Header header = new Header("X-Road-Client", subscriptionIdentifier)
        switch (problem) {
            case "missing eventType in body" ->
                data.remove("eventType")
            case "PUSH subscription missing pushUrl" ->
                data = TestData.addEventType(["method": "PUSH"], eventTypeIdentifier)
            case "non-existent room" ->
                roomIdentifier = "non-existent"
            case "terminated room" ->
                db.terminateRoom(roomId)
            case "non-existent event type" ->
                data = TestData.addEventType(TestData.pullSubscriptionParameter, "non-existent event type")
            case "terminated event type" ->
                db.terminateEventType(eventTypeId)
            case "'X-Road-Client' header missing" ->
                header = null
            case "'X-Road-Client' header not matching 'memberId'" ->
                header = new Header("X-Road-Client", "non-existent")
            case "member already has 1 active PULL subscription for this event type" ->
                ManagementApi.subscribe(subscriptionIdentifier, roomIdentifier, data).then().statusCode(200)
            case "member already has 1 active PUSH subscription with the same pushUrl and subscriptionIdentifier for this event type" ->
                {
                    data = TestData.addEventType(TestData.getValidPushSubscriptionParameter(), eventTypeIdentifier)
                    ManagementApi.subscribe(subscriptionIdentifier, roomIdentifier, data).then().statusCode(200)
                }
        }
        Response response = ManagementApi.subscribe(subscriptionIdentifier, roomIdentifier, data, header)

        then: "subscribe fails"
        response.then().statusCode(statusCode)
        switch (statusCode) {
            case 403 ->
                response.then().body("timestamp", is(not(null)),
                        "status", is(statusCode),
                        "error", is(message),
                        "path", is("/rooms/$subscriptionIdentifier/$roomIdentifier/subscriptions".toString()))
            case 404 ->
                response.then().body("message", is(message))
            case 500 ->
                response.then().body("message", matchesPattern(message))
        }

        where:
        problem                                                             || statusCode | message
        "missing eventType in body"                                         || 500        | Utils.technicalErrorRegex
        "PUSH subscription missing pushUrl"                                 || 500        | Utils.technicalErrorRegex
        "non-existent room"                                                 || 404        | "Room was not found"
        "terminated room"                                                   || 404        | "Room was not found"
        "non-existent event type"                                           || 404        | "Event Type was not found"
        "terminated event type"                                             || 404        | "Event Type was not found"
        "'X-Road-Client' header missing"                                    || 403        | "Forbidden"
        "'X-Road-Client' header not matching 'memberId'"                    || 403        | "Forbidden"
        "member already has 1 active PULL subscription for this event type" || 500        | Utils.technicalErrorRegex
        "member already has 1 active PUSH subscription with the same pushUrl " +
                "and subscriptionIdentifier for this event type"            || 500        | Utils.technicalErrorRegex
    }

}
