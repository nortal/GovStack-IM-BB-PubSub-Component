package com.govstack.information_mediator.pubsub.api.management.rooms

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

import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.not

@Tag("PubSub")
@Tag("API")
@Epic("Management-api")
@Feature("GET /rooms/{memberId}/{roomId}")
class GetRoomDetailsTests extends GenericSpecification {

    String uuid, managerId, roomId
    String managerIdentifier, roomIdentifier

    def setup() {
        uuid = UUIDGenerator.randomUUID()
        (managerIdentifier, roomIdentifier) = [TestData.getXRoadManagerIdentifier(uuid), "room-$uuid"]
        managerId = db.addManager(managerIdentifier)
        roomId = db.addRoom(managerId, roomIdentifier)
    }

    @Story("Successful get room details")
    def "Given: a manager with a room; When: manager gets their room details; Then: getting room details succeeds and correct details are returned"() {
        given: "a manager with a room"

        when: "manager gets their room details"
        Response response = ManagementApi.getRoomDetails(managerIdentifier, roomIdentifier)

        then: "getting room details succeeds"
        response.then().statusCode(200)

        and: "correct details are returned"
        response.then().body("identifier", is(roomIdentifier),
                "managerIdentifier", is(managerIdentifier),
                "deliveryDelay", is(1),
                "deliveryDelayMultiplier", is(1.01F),
                "deliveryAttempts", is(3),
                //TODO: Replace with actual timestamp check
                "createdAt", is(not(null)),
                "createdBy", is("TESTS"))
    }

    @Story("Unsuccessful get room details")
    def "Given: a manager with a room; When: manager gets their room details with a problem '#problem'; Then: getting room details fails with #statusCode: '#message'"() {
        given: "a manager with a room"

        when: "manager gets their room details with a problem"
        Header header = new Header("X-Road-Client", managerIdentifier)
        switch (problem) {
            case "non-existent manager" ->
                managerIdentifier = "non-existent"
            case "terminated manager" ->
                db.terminateManager(managerId)
            case "non-existent room" ->
                roomIdentifier = "non-existent"
            case "terminated room" ->
                db.terminateRoom(roomId)
            case "other manager's room" ->
                {
                    managerIdentifier = TestData.getXRoadManagerIdentifier("2-$uuid")
                    db.addManager(managerIdentifier)
                }
            case "'X-Road-Client' header missing" ->
                header = null
            case "'X-Road-Client' header not matching 'memberId'" ->
                header = new Header("X-Road-Client", "non-existent")
        }
        Response response = ManagementApi.getRoomDetails(managerIdentifier, roomIdentifier, header)

        then: "getting room details fails"
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
        "non-existent manager"                           || 403        | "Forbidden"
        "terminated manager"                             || 404        | "Manager was not found"
        "non-existent room"                              || 404        | "Room was not found"
        "terminated room"                                || 404        | "Room was not found"
        "other manager's room"                           || 403        | "Forbidden"
        "'X-Road-Client' header missing"                 || 403        | "Forbidden"
        "'X-Road-Client' header not matching 'memberId'" || 403        | "Forbidden"
    }

}
