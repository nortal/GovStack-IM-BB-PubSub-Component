package com.govstack.information_mediator.pubsub.requests

import com.govstack.information_mediator.pubsub.config.ConfigHolder
import io.qameta.allure.Step
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

import static io.restassured.RestAssured.given

class ManagementApi {

    static String baseUrl = ConfigHolder.getConf().managementApiUrl()

    @Step("GET /rooms/{memberId}")
    static Response getRooms(String memberId, Map params, Header header = new Header("X-Road-Client", memberId)) {
        RequestSpecification request = given().contentType(ContentType.JSON)
        if (params != null) {
            request.params(params)
        }
        if (header != null) {
            request.header(header)
        }
        return request.when().get("${baseUrl}/rooms/${memberId}")
    }

    @Step("GET /rooms/{memberId}/{roomId}")
    static Response getRoomDetails(String memberId, String roomId, Header header = new Header("X-Road-Client", memberId)) {
        RequestSpecification request = given().contentType(ContentType.JSON)
        if (header != null) {
            request.header(header)
        }
        return request.when().get("${baseUrl}/rooms/${memberId}/${roomId}")
    }

    @Step("PUT /rooms/{memberId}/{roomId}")
    static Response editRoom(String memberId, String roomId, Map body, Header header = new Header("X-Road-Client", memberId)) {
        RequestSpecification request = given().contentType(ContentType.JSON)
        if (header != null) {
            request.header(header)
        }
        return request.body(body).when().put("${baseUrl}/rooms/${memberId}/${roomId}")
    }

    @Step("DELETE /rooms/{memberId}/{roomId}")
    static Response terminateRoom(String memberId, String roomId, Header header = new Header("X-Road-Client", memberId)) {
        RequestSpecification request = given().contentType(ContentType.JSON)
        if (header != null) {
            request.header(header)
        }
        return request.when().delete("${baseUrl}/rooms/${memberId}/${roomId}")
    }

    @Step("POST /rooms/{memberId}/{roomId}/subscriptions")
    static Response subscribe(String memberId, String roomId, Map body, Header header = new Header("X-Road-Client", memberId)) {
        RequestSpecification request = given().contentType(ContentType.JSON)
        if (header != null) {
            request.header(header)
        }
        return request.body(body).when().post("${baseUrl}/rooms/${memberId}/${roomId}/subscriptions")
    }

    @Step("DELETE /rooms/{memberId}/{roomId}/subscriptions/{subscriptionId}")
    static Response unsubscribe(String memberId, String roomId, String subscriptionId, Header header = new Header("X-Road-Client", memberId)) {
        RequestSpecification request = given().contentType(ContentType.JSON)
        if (header != null) {
            request.header(header)
        }
        return request.when().delete("${baseUrl}/rooms/${memberId}/${roomId}/subscriptions/${subscriptionId}")
    }

    @Step("GET /rooms/{memberId}/{roomId}/subscriptions")
    static Response listSubscriptions(String memberId, String roomId, String status = null, Header header = new Header("X-Road-Client", memberId)) {
        RequestSpecification request = given().contentType(ContentType.JSON)
        if (header != null) {
            request.header(header)
        }
        if (status != null) {
            request.param("status", status)
        }
        return request.when().get("${baseUrl}/rooms/${memberId}/${roomId}/subscriptions")
    }

}
