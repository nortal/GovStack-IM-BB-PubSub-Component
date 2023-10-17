package com.govstack.information_mediator.pubsub.api.management.rooms

import com.govstack.information_mediator.pubsub.UseCase
import com.govstack.information_mediator.pubsub.Utils
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

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.not

@Tag("PubSub")
@Tag("API")
@Epic("Management-api")
@Feature("GET /rooms/{memberId}")
class GetRoomsTests extends GenericSpecification {

    String uuid, managerId, managerIdentifier

    def setup() {
        uuid = UUIDGenerator.randomUUID()
        managerIdentifier = TestData.getXRoadManagerIdentifier(uuid)
        managerId = db.addManager(managerIdentifier)
    }

    @UseCase("UC3.2")
    @Story("UC3.2 - Successful get rooms")
    def "Given: a manager with multiple rooms; When: member gets their rooms with pageSortingParameters #pagingSortingParameters; Then: getting rooms succeeds and correct rooms are returned"() {
        given: "a manager with multiple rooms"
        ArrayList rooms = []
        for (i in 11..20) {
            String roomIdentifier = "room$i-$uuid"
            db.addRoom(managerId, roomIdentifier)
            rooms.add(["identifier": roomIdentifier, "managerIdentifier": managerIdentifier])
        }

        when: "member gets their rooms"
        Response response = ManagementApi.getRooms(managerIdentifier, pagingSortingParameters)

        then: "getting rooms succeeds"
        response.then().statusCode(200)

        and: "correct rooms are returned"
        Map actualResponse = (Map) Utils.objectToMap(response.then().extract().body().asString())
        assertThat(actualResponse["rooms"].size(), is(returnedRoomsCount))
        ArrayList expectedRooms = null
        switch (desc) {
            case "all items ASC" ->
                expectedRooms = rooms
            case "all items DESC" ->
                {
                    rooms.sort({ mapb, mapa -> mapa.identifier.compareTo(mapb.identifier) })
                    expectedRooms = rooms
                }
            case "limit to 1" ->
                expectedRooms = [rooms[0]]
            case "limit to 1, offset 1" ->
                expectedRooms = [rooms[1]]
        }
        Map expectedResults = ["rooms"         : expectedRooms,
                               "pagingMetadata": pagingMetadata]
        assertThat(actualResponse, is(expectedResults))

        where:
        desc                   | pagingSortingParameters   || returnedRoomsCount | pagingMetadata
        "all items ASC"        | null                      || 10                 | ["items": 10, "limit": 25, "offset": 0, "total_items": 10]
        "all items DESC"       | ["desc": true]            || 10                 | ["items": 10, "limit": 25, "offset": 0, "total_items": 10]
        "limit to 1"           | ["limit": 1]              || 1                  | ["items": 1, "limit": 1, "offset": 0, "total_items": 10]
        "limit to 1, offset 1" | ["limit": 1, "offset": 1] || 1                  | ["items": 1, "limit": 1, "offset": 1, "total_items": 10]
    }

    @UseCase("UC3.2")
    @Story("UC3.2 - Successful get rooms")
    def "Given: a manager with #desc; When: member gets their rooms; Then: getting rooms succeeds and an empty list is returned"() {
        given: "a manager"
        if (desc == "a terminated room") {
            String roomId = db.addRoom(managerId, "room-$uuid")
            db.terminateRoom(roomId)
        }

        when: "member gets their rooms"
        Response response = ManagementApi.getRooms(managerIdentifier, null)

        then: "getting rooms succeeds"
        response.then().statusCode(200)

        and: "an empty list is returned"
        Map actualResponse = (Map) Utils.objectToMap(response.then().extract().body().asString())
        Map expectedResults = ["rooms"         : [],
                               "pagingMetadata": ["items"      : 0,
                                                  "limit"      : 25,
                                                  "offset"     : 0,
                                                  "total_items": 0]]
        assertThat(actualResponse, is(expectedResults))

        where:
        desc                | _
        "no rooms"          | _
        "a terminated room" | _
    }

    @UseCase("UC3.2")
    @Story("UC3.2 - Unsuccessful get rooms")
    def "Given: a manager; When: member gets their rooms with a problem: #problem; Then: getting rooms fails"() {
        given: "a manager"

        when: "member gets their rooms"
        Header header = new Header("X-Road-Client", managerIdentifier)
        switch (problem) {
            case "non-existent manager" ->
                managerIdentifier = "non-existent"
            case "terminated manager" ->
                db.terminateManager(managerId)
            case "'X-Road-Client' header missing" ->
                header = null
            case "'X-Road-Client' header not matching 'memberId'" ->
                header = new Header("X-Road-Client", "non-existent")
        }
        Response response = ManagementApi.getRooms(managerIdentifier, null, header)

        then: "getting rooms fails"
        response.then().statusCode(statusCode)
        switch (statusCode) {
            case 403 ->
                response.then().body("timestamp", is(not(null)),
                        "status", is(statusCode),
                        "error", is(message),
                        "path", is("/rooms/${URLEncoder.encode(managerIdentifier, "UTF-8")}".toString()))
            case 404 ->
                response.then().body("message", is(message))
        }

        where:
        problem                                          || statusCode | message
        "non-existent manager"                           || 403        | "Forbidden"
        "terminated manager"                             || 404        | "Manager was not found"
        "'X-Road-Client' header missing"                 || 403        | "Forbidden"
        "'X-Road-Client' header not matching 'memberId'" || 403        | "Forbidden"
    }

}
