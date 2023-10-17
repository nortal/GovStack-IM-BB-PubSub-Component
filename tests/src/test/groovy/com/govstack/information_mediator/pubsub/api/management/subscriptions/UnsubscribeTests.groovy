package com.govstack.information_mediator.pubsub.api.management.subscriptions

import com.govstack.information_mediator.pubsub.UseCase
import com.govstack.information_mediator.pubsub.Utils
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator
import com.govstack.information_mediator.pubsub.config.GenericSpecification
import com.govstack.information_mediator.pubsub.config.TestData
import com.govstack.information_mediator.pubsub.domain.entity.Subscription
import com.govstack.information_mediator.pubsub.requests.ManagementApi
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
@Epic("Management-api")
@Feature("DELETE /rooms/{memberId}/{roomId}/subscriptions/{subscriptionId}")
class UnsubscribeTests extends GenericSpecification {

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

    @UseCase("UC4.7")
    @Story("UC4.7 - Successful unsubscribe")
    def "Given: member's #subscriptionStatus subscription; When: member unsubscribes; Then: unsubscribe succeeds and correct data is saved in DB"() {
        given: "member's ACTIVE subscription"
        String subscriptionId = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier)
        if (subscriptionStatus == "ACTIVE") {
            db.changeSubscriptionStatus(subscriptionId, Subscription.Status.ACTIVE)
        }

        when: "member unsubscribes"
        Response response = ManagementApi.unsubscribe(subscriptionIdentifier, roomIdentifier, subscriptionId)

        then: "unsubscribe succeeds"
        response.then().statusCode(200)

        and: "correct data is saved in DB"
        GroovyRowResult subscription = db.getSubscription(subscriptionId)[0]
        assertThat(subscription["deleted_at"], is(not(null)))

        GroovyRowResult lastSubscriptionStatus = db.getSubscriptionStatuses(subscriptionId)[0]
        assertThat(lastSubscriptionStatus["status"], is("TERMINATED"))

        where:
        subscriptionStatus | _
        "ACTIVE"           | _
        "PENDING"          | _
    }

    @UseCase("UC4.7")
    @Story("UC4.7 - Unsuccessful unsubscribe")
    def "Given: member's subscription; When: member unsubscribes with problem '#problem'; Then: unsubscribe fails with #statusCode"() {
        given: "member's subscription"
        String subscriptionId = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier)
        db.changeSubscriptionStatus(subscriptionId, Subscription.Status.ACTIVE)
        Header header = new Header("X-Road-Client", subscriptionIdentifier)
        when: "member unsubscribes with problem"
        switch (problem) {
            case "subscription already terminated" ->
                ManagementApi.unsubscribe(subscriptionIdentifier, roomIdentifier, subscriptionId).then().statusCode(200)
            case "non-existent subscriptionId" ->
                {
                    subscriptionId = "non-existent"
                    header = new Header("X-Road-Client", subscriptionIdentifier)
                }
            case "subscription in another room" ->
                {
                    String room2Identifier = "2-$roomIdentifier"
                    String room2Id = db.addRoom(managerId, room2Identifier)
                    String eventType2Id = db.addEventType(room2Id, "2-$eventTypeIdentifier")
                    eventTypeVersionId = db.addEventTypeVersion(eventType2Id)
                    subscriptionId = db.addSubscription(room2Id, eventType2Id, subscriptionIdentifier)
                }
            case "non-existent room" ->
                roomIdentifier = "non-existent"
            case "terminated room" ->
                db.terminateRoom(roomId)
            case "'X-Road-Client' header missing" ->
                header = null
            case "'X-Road-Client' header not matching 'memberId'" ->
                header = new Header("X-Road-Client", "non-existent")
        }
        Response response = ManagementApi.unsubscribe(subscriptionIdentifier, roomIdentifier, subscriptionId, header)

        then: "unsubscribe fails"
        response.then().statusCode(statusCode)
        switch (statusCode) {
            case 403 ->
                response.then().body("timestamp", is(not(null)),
                        "status", is(statusCode),
                        "error", is(message),
                        "path", is("/rooms/${URLEncoder.encode(subscriptionIdentifier, "UTF-8")}/$roomIdentifier/subscriptions/$subscriptionId".toString()))
            case 404 ->
                response.then().body("message", is(message))
            case 500 ->
                response.then().body("message", matchesPattern(message))
        }

        where:
        problem                                          || statusCode | message
        "subscription already terminated"                || 404        | "Subscription was not found"
        "non-existent subscriptionId"                    || 500        | Utils.technicalErrorRegex
        "subscription in another room"                   || 404        | "Subscription was not found"
        "non-existent room"                              || 404        | "Room was not found"
        "terminated room"                                || 404        | "Room was not found"
        "'X-Road-Client' header missing"                 || 403        | "Forbidden"
        "'X-Road-Client' header not matching 'memberId'" || 403        | "Forbidden"
    }

}
