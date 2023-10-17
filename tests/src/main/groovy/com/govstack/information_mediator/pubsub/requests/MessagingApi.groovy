package com.govstack.information_mediator.pubsub.requests

import com.govstack.information_mediator.pubsub.config.ConfigHolder
import io.qameta.allure.Step
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

import static io.restassured.RestAssured.given

class MessagingApi {

    static String baseUrl = ConfigHolder.getConf().messagingApiUrl()

    @Step("POST /rooms/{memberId}/{roomId}/publish")
    static Response publishMessage(String memberId, String roomId, Map message, Header header = new Header("X-Road-Client", memberId)) {
        RequestSpecification request = given().contentType(ContentType.JSON).body(message)
        if (header != null) {
            request.header(header)
        }
        return request.when().post("${baseUrl}/rooms/${memberId}/${roomId}/publish")
    }

    @Step("GET /rooms/{memberId}/{roomId}/pull")
    static Response pullMessage(String memberId, String roomId, String subscriptionId, Header header = new Header("X-Road-Client", memberId)) {
        RequestSpecification request = given().contentType(ContentType.JSON)
        if (subscriptionId != null) request.params("subscriptionId", subscriptionId)
        if (header != null) {
            request.header(header)
        }
        return request.when().get("${baseUrl}/rooms/${memberId}/${roomId}/pull")
    }

}
