package com.govstack.information_mediator.pubsub.api.management.rooms

import com.govstack.information_mediator.pubsub.UseCase
import com.govstack.information_mediator.pubsub.Utils
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator
import com.govstack.information_mediator.pubsub.config.GenericSpecification
import com.govstack.information_mediator.pubsub.config.TestData
import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType
import com.govstack.information_mediator.pubsub.domain.entity.Subscription
import com.govstack.information_mediator.pubsub.requests.ManagementApi
import groovy.sql.GroovyRowResult
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Issue
import io.qameta.allure.Story
import io.restassured.http.Header
import io.restassured.response.Response
import spock.lang.Tag

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.not

@Tag("PubSub")
@Tag("API")
@Epic("Management-api")
@Feature("DELETE /rooms/{memberId}/{roomId}")
class DeleteRoomTests extends GenericSpecification {

    String uuid, managerId, roomId, eventTypeId, eventTypeVersionId, publisherId, subscriptionId
    String managerIdentifier, roomIdentifier, eventTypeIdentifier, publisherIdentifier, subscriptionIdentifier

    def setup() {
        uuid = UUIDGenerator.randomUUID()
        (managerIdentifier, roomIdentifier, eventTypeIdentifier, publisherIdentifier, subscriptionIdentifier) =
                [TestData.getXRoadManagerIdentifier(uuid), "room-$uuid", "EVENT-$uuid", TestData.getXRoadPublisherIdentifier(uuid),
                 TestData.getXRoadSubscriberIdentifier(uuid)]
    }

    @UseCase("UC3.10")
    @Story("UC3.10 - Successful room termination")
    def "Given: a room with a event type, publisher and subscriber; When: manager terminates the room; Then: room termination succeeds and connected data is terminated"() {
        given: "a room with a event type, publisher and subscriber"
        managerId = db.addManager(managerIdentifier)
        roomId = db.addRoom(managerId, roomIdentifier)
        eventTypeId = db.addEventType(roomId, eventTypeIdentifier)
        eventTypeVersionId = db.addEventTypeVersion(eventTypeId)
        subscriptionId = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier)
        publisherId = db.addPublisher(roomId, publisherIdentifier)
        String publisherConstraintId = db.addPublisherConstraint(publisherId, eventTypeId)

        when: "manager terminates the room"
        Response response = ManagementApi.terminateRoom(managerIdentifier, roomIdentifier)

        then: "room termination succeeds"
        response.then().statusCode(204)

        and: "connected data is terminated"
        // rooms
        GroovyRowResult room = db.getRoom(roomId)[0]
        assertThat(room.deleted_at, is(not(null)))
        assertJournalEntry(room.journal)

        // event_types
        GroovyRowResult eventType = db.getEventTypes(eventTypeId)[0]
        assertThat(eventType.deleted_at, is(not(null)))
        assertJournalEntry(eventType.journal)

        // event_type_versions
        GroovyRowResult eventTypeVersion = db.getEventTypeVersions(eventTypeVersionId)[0]
        assertThat(eventTypeVersion.deleted_at, is(not(null)))
        assertJournalEntry(eventTypeVersion.journal)

        // publishers
        GroovyRowResult publisher = db.getPublishers(publisherId)[0]
        assertThat(publisher.deleted_at, is(not(null)))
        assertJournalEntry(publisher.journal)

        // publisher_constraints
        GroovyRowResult publisherConstraint = db.getPublisherConstraints(publisherConstraintId)[0]
        assertThat(publisherConstraint.deleted_at, is(not(null)))

        // subscriptions
        GroovyRowResult subscription = db.getSubscription(subscriptionId)[0]
        assertThat(subscription.deleted_at, is(not(null)))
        assertJournalEntry(subscription.journal)

        // subscription_status
        GroovyRowResult subscriptionStatus = db.getSubscriptionStatuses(subscriptionId)[0]
        assertThat(subscriptionStatus.status, is("TERMINATED"))
    }

    void assertJournalEntry(Object journal) {
        Map lastJournalEntry = (Map) Utils.objectToMap(journal)[-1]
        assertThat(lastJournalEntry.at, is(not(null)))
        assertThat(lastJournalEntry.by, is(managerIdentifier))
        assertThat(lastJournalEntry.action, is("MODIFIED"))
    }

    @Issue("M604017-174")
    @UseCase("UC3.10")
    @Story("UC3.10 - Unsuccessful room termination")
    def "Given: a manager with a room; When: manager terminates the room with a problem: #problem; Then: terminating room fails with #statusCode: #message"() {
        given: "a manager with a room"
        IdentifierType identifierType = (problem == "INTERNAL manager accessed over XRoad") ? IdentifierType.INTERNAL : IdentifierType.XROAD
        managerId = db.addManager(managerIdentifier, identifierType)
        roomId = db.addRoom(managerId, roomIdentifier)
        eventTypeId = db.addEventType(roomId, eventTypeIdentifier)
        eventTypeVersionId = db.addEventTypeVersion(eventTypeId)
        subscriptionId = db.addSubscription(roomId, eventTypeId, subscriptionIdentifier)
        db.changeSubscriptionStatus(subscriptionId, Subscription.Status.ACTIVE)
        publisherId = db.addPublisher(roomId, publisherIdentifier)
        db.addPublisherConstraint(publisherId, eventTypeId)

        when: "manager terminates the room"
        Header header = new Header("X-Road-Client", managerIdentifier)
        switch (problem) {
            case "non-existent manager" ->
                managerIdentifier = "non-existent"
            case "terminated manager" ->
                db.terminateManager(managerId)
            case "other member's room" ->
                {
                    managerIdentifier = TestData.getXRoadManagerIdentifier("2-$uuid")
                    managerId = db.addManager(managerIdentifier)
                    roomIdentifier = "room2-$uuid"
                    roomId = db.addRoom(managerId, roomIdentifier)
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
        Response response = ManagementApi.terminateRoom(managerIdentifier, roomIdentifier, header)

        then: "terminating room fails"
        response.then().statusCode(statusCode)
        switch (statusCode) {
            case 403 ->
                response.then().body("timestamp", is(not(null)),
                        "status", is(statusCode),
                        "error", is(message),
                        "path", is("/rooms/${URLEncoder.encode(managerIdentifier, "UTF-8")}/$roomIdentifier".toString()))
            case 404 ->
                response.then().body("message", is(message))
        }

        where:
        problem                                          || statusCode | message
//        "INTERNAL manager accessed over XRoad"           || 403        | "Forbidden"
        "non-existent manager"                           || 403        | "Forbidden"
        "terminated manager"                             || 404        | "Manager was not found"
        "other member's room"                            || 403        | "Forbidden"
        "non-existent room"                              || 404        | "Room was not found"
        "terminated room"                                || 404        | "Room was not found"
        "'X-Road-Client' header missing"                 || 403        | "Forbidden"
        "'X-Road-Client' header not matching 'memberId'" || 403        | "Forbidden"
    }

}
