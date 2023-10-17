package perftest


import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.status
import java.time.Duration

class PublishPullSimulation : Simulation() {

    private val publishPullScenario = scenario("publish-pull")
            .feed(pullRoomFeeder().circular())
            .group("publish-pull").on(
                    exec(publishMessage("#{publisher}", "#{room}", "#{event_type}", "any text"))
                            .doWhileDuring({ session -> session.getInt("pullStatus") != 200 }, Duration.ofMillis(2_000)
                            ).on(
                                    pause(Duration.ofMillis(100)).exec(
                                            pullMessage("#{subscriber}", "#{room}").check(status().within(200, 404).saveAs("pullStatus"))
                                    )
                            )
            )

    init {
        setUp(
                publishPullScenario.injectOpen(atOnceUsers(20))
        ).protocols(Protocol.http)
    }
}
