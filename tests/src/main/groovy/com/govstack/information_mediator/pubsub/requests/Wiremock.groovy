package com.govstack.information_mediator.pubsub.requests

import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator
import com.govstack.information_mediator.pubsub.config.ConfigHolder
import io.qameta.allure.Step
import io.restassured.response.Response

import static io.restassured.RestAssured.given

class Wiremock {

    static String adminUrl = ConfigHolder.getConf().wiremockAdminUrl()

    @Step("WIREMOCK POST /mappings")
    static void postMapping(Map mappings = getCallbackMapping()) {
        given()
                .when()
                .body(mappings)
                .post(adminUrl + "/mappings")
                .then()
                .statusCode(201)
    }

    @Step("WIREMOCK POST /requests/find")
    static Response findRequests(Map data) {
        Response response = given()
                .when()
                .body(data)
                .post(adminUrl + "/requests/find")
        response.then().statusCode(200)
        return response
    }

    static Map getCallbackMapping(String uuid = UUIDGenerator.randomUUID(), int responseStatus = 200) {
        return [
                "request" : [
                        "method": "POST",
                        "url"   : "/api/callback/" + uuid
                ],
                "response": [
                        "status": responseStatus
                ]
        ]
    }

}
