package perftest

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.CoreDsl.constantConcurrentUsers
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl

class PublishSingleSubscriberPushSimulation : Simulation() {

    private val publishPushToSingleSubscriberScenario = scenario("publish-to-single-subscriber")
            .feed(singleSubscriberPushRoomFeeder().circular())
            .exec(
                    HttpDsl.http("publish-and-forget")
                            .post("/rooms/#{publisher}/#{room}/publish").header("X-Road-Client", "#{publisher}")
                            .body(CoreDsl.StringBody("""{"eventType":"#{event_type}","content":{"payload":"test"}}"""))
            )
    init {
        setUp(
                publishPushToSingleSubscriberScenario.injectClosed(constantConcurrentUsers(20).during(10))
        ).protocols(
                Protocol.http, Protocol.database
        )
    }

}
