package com.govstack.information_mediator.pubsub.api.management.rooms

import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator
import com.govstack.information_mediator.pubsub.config.GenericSpecification
import com.govstack.information_mediator.pubsub.config.TestData
import com.govstack.information_mediator.pubsub.requests.ManagementApi
import com.govstack.information_mediator.pubsub.Utils
import groovy.sql.GroovyRowResult
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.restassured.response.Response
import spock.lang.Tag

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.is

@Tag("PubSub")
@Tag("API")
@Epic("Management-api")
@Feature("PUT /rooms/{memberId}/{roomId}")
class EditRoomTests extends GenericSpecification {


    String uuid, managerId, roomId
    String managerIdentifier, roomIdentifier

    def setup() {
        uuid = UUIDGenerator.randomUUID()
        (managerIdentifier, roomIdentifier) = [TestData.getXRoadManagerIdentifier(uuid), "room-$uuid"]
    }

    @Story("Successful room editing")
    def "Given: a room; When: manager edits the room; Then: editing room succeeds and new config returned"() {
        given: "a room"
        managerId = db.addManager(managerIdentifier)
        roomId = db.addRoom(managerId, roomIdentifier, TestData.roomConfiguration)

        when: "manager edits the room"
        Map newConfig = ["deliveryDelay"          : 10,
                         "deliveryAttempts"       : 5,
                         "messageExpiration"      : 1000000,
                         "deliveryDelayMultiplier": 1.1F]
        Response response = ManagementApi.editRoom(managerIdentifier, roomIdentifier, newConfig)

        then: "editing room succeeds"
        response.then().statusCode(200)

        and: "new config returned"
        response.then().body("identifier", is(roomIdentifier),
                "managerIdentifier", is(managerIdentifier),
                "messageExpiration", is(newConfig.messageExpiration),
                "deliveryDelay", is(newConfig.deliveryDelay),
                "deliveryDelayMultiplier", is(newConfig.deliveryDelayMultiplier),
                "deliveryAttempts", is(newConfig.deliveryAttempts))

    }

}
