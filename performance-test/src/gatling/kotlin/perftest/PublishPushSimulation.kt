package perftest

import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.Simulation
import java.time.Duration


class PublishPushSimulation : Simulation() {

    private val publishPushScenario = scenario("publish-push")
            .feed(multipleSubscriberPushRoomFeeder().circular())
            .group("publish-push").on(
                    exec { session ->
                        val randomString = randomString(12)
                        session.set("randomString", randomString)
                    }
                            .exec(publishMessage("#{publisher}", "#{room}", "#{event_type}", "#{randomString}")
                                    .check(jsonPath("$.eventId").saveAs("eventId"))) // Capture the eventId
                            .tryMax(50).on(
                                    pause(Duration.ofMillis(500))
                                            .exec { session ->
                                                val eventId = session.getString("eventId") // Read the eventId
                                                checkMessagesPublished(eventId)
                                                checkMessagesDelivered(eventId)
                                                session
                                            }
                            )
            )

    init {
        setUp(
                publishPushScenario.injectClosed(constantConcurrentUsers(1).during(30))
        ).protocols(
                Protocol.http, Protocol.database
        )
    }

}
