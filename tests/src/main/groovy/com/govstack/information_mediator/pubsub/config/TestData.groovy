package com.govstack.information_mediator.pubsub.config

import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator

class TestData {

    static String roomConfiguration =
            '{"messageExpiration": 0, ' +
                    '"deliveryDelay": 1, ' +
                    '"deliveryDelayMultiplier": 1.01, ' +
                    '"deliveryAttempts": 3}'

    static String jsonSchema = '{\n' +
            '   "$schema":"https://json-schema.org/draft/2020-12/schema",\n' +
            '   "$id":"https://example.com/event-type.schema.json",\n' +
            '   "title":"EVENT-001 Schema",\n' +
            '   "description":"Any content",\n' +
            '   "type":"object",\n' +
            '   "properties":{\n' +
            '      "payload":{\n' +
            '         "type":"string",\n' +
            '         "description":"The content of the payload"\n' +
            '      }\n' +
            '   },\n' +
            '   "required":[\n' +
            '      "payload"\n' +
            '   ]\n' +
            '}'
    static String jsonSchema2 = '{\n' +
            '   "$schema":"https://json-schema.org/draft/2020-12/schema",\n' +
            '   "$id":"https://example.com/event-type.schema.json",\n' +
            '   "title":"EVENT-001 Schema",\n' +
            '   "description":"Any content",\n' +
            '   "type":"object",\n' +
            '   "properties":{\n' +
            '      "payload2":{\n' +
            '         "type":"string",\n' +
            '         "description":"The content of the payload"\n' +
            '      }\n' +
            '   },\n' +
            '   "required":[\n' +
            '      "payload2"\n' +
            '   ]\n' +
            '}'
    static Map pullSubscriptionParameter = ["method": "PULL"]

    static Map getValidPushSubscriptionParameter(String url = "/${UUIDGenerator.randomUUID()}") {
        return ["method" : "PUSH",
                "pushUrl": url
        ]
    }

    static Map getValidMessageData(String eventType, Map content = ["payload": "test"]) {
        return ["eventType": eventType,
                "content"  : content
        ]
    }

    static Map getPullSubscriptionParameterWithEventType(String eventTypeIdentifier) {
        Map data = ["eventType": eventTypeIdentifier]
        data.putAll(pullSubscriptionParameter)
        return data
    }

    static Map addEventType(Map map, String eventTypeIdentifier) {
        Map newMap = (Map) map.clone()
        newMap.putAll(["eventType": eventTypeIdentifier])
        return newMap
    }

    static String getXRoadManagerIdentifier(String uniquenessString) {
        return "SANDBOX:ORG:TEST:MANAGER-$uniquenessString"
    }

    static String getXRoadSubscriberIdentifier(String uniquenessString) {
        return "SANDBOX:ORG:TEST:SUBSCRIBER-$uniquenessString"
    }

    static String getXRoadPublisherIdentifier(String uniquenessString) {
        return "SANDBOX:ORG:TEST:PUBLISHER-$uniquenessString"
    }

    static String getInternalManagerIdentifier(String uniquenessString = UUIDGenerator.randomUUID().toString()) {
        return "$uniquenessString-internal-manager"
    }

}
