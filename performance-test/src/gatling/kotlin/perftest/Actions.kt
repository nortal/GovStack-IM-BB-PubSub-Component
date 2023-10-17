package perftest

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpRequestActionBuilder
import io.gatling.javaapi.jdbc.JdbcDsl
import ru.tinkoff.load.javaapi.JdbcDsl.jdbc
import ru.tinkoff.load.javaapi.JdbcDsl.simpleCheck
import ru.tinkoff.load.javaapi.actions.QueryActionBuilder
import ru.tinkoff.load.javaapi.check.simpleCheckType.Empty
import ru.tinkoff.load.javaapi.check.simpleCheckType.NonEmpty


fun pullMessage(subscriber: String, room: String): HttpRequestActionBuilder =
        http("pull")
                .get("/rooms/$subscriber/$room/pull")
                .header("X-Road-Client", subscriber)

fun publishMessage(publisher: String, room: String, eventType: String, anyText: String): HttpRequestActionBuilder =
        http("publish")
                .post("/rooms/$publisher/$room/publish").header("X-Road-Client", publisher)
                .body(CoreDsl.StringBody("""{"eventType":"$eventType","content":{"payload":"$anyText"}}"""))


fun multipleSubscriberPushRoomFeeder() = JdbcDsl.jdbcFeeder(
        Configuration.get(DB_JDBC_URL), Configuration.get(DB_USER), Configuration.get(DB_PASS),
        """
                |SELECT distinct on (r.id) r.identifier as room, p.identifier as publisher, et.identifier as event_type
                |FROM rooms r
                |    JOIN publishers p on r.id = p.room_id
                |    JOIN event_types et on r.id = et.room_id
                |WHERE exists(select 1 from subscriptions s where s.room_id = r.id and s.parameters ->>'method' = 'PUSH')
                |    and (select count(id) from subscriptions s where s.room_id = r.id and s.parameters ->>'method' = 'PUSH') > 1
                |ORDER BY r.id, p.id, et.id
                |""".trimMargin()
)

fun singleSubscriberPushRoomFeeder() = JdbcDsl.jdbcFeeder(
        Configuration.get(DB_JDBC_URL), Configuration.get(DB_USER), Configuration.get(DB_PASS),
        """
                |SELECT distinct on (r.id) r.identifier as room, p.identifier as publisher, et.identifier as event_type
                |FROM rooms r
                |    JOIN publishers p on r.id = p.room_id
                |    JOIN event_types et on r.id = et.room_id
                |WHERE exists(select 1 from subscriptions s where s.room_id = r.id and s.parameters ->>'method' = 'PUSH')
                |    and (select count(id) from subscriptions s where s.room_id = r.id and s.parameters ->>'method' = 'PUSH') = 1
                |ORDER BY r.id, p.id, et.id
                |""".trimMargin()
)

fun pullRoomFeeder() = JdbcDsl.jdbcFeeder(
        Configuration.get(DB_JDBC_URL), Configuration.get(DB_USER), Configuration.get(DB_PASS),
        """
            |SELECT distinct on (r.id) r.identifier as room, p.identifier as publisher,
            |                          et.identifier as event_type, s.identifier as subscriber
            |FROM rooms r
            |         join publishers p on r.id = p.room_id
            |         join event_types et on r.id = et.room_id
            |         join subscriptions s on r.id = s.room_id
            |WHERE s.parameters ->> 'method' = 'PULL'
            |ORDER BY r.id, p.id, et.id, s.id;
        """.trimMargin())


fun checkMessagesDelivered(eventId: String?): QueryActionBuilder =
        jdbc("Check all messages delivered")
                .queryP("""
                |SELECT * FROM (
                |    SELECT
                |        count(published_at) published,
                |        count(delivered_at) as delivered
                |    FROM
                |	     published_messages
                |	 JOIN im_msg.events e on (published_messages.event_id = e.id)
                |	 WHERE published_messages.event_id = {eventId}) counts
                |WHERE counts.published != counts.delivered
                |""".trimMargin()
                ).params(mapOf("eventId" to eventId))
                .check(simpleCheck(Empty))

fun checkMessagesPublished(eventId: String?): QueryActionBuilder =
        jdbc("Check messages published")
                .queryP("""
                |SELECT
                |    id
                |FROM
                |    published_messages
                |WHERE
                |    event_id = {eventId} and published_at is not null
                """.trimMargin()
                )
                .params(mapOf("eventId" to eventId))
                .check(simpleCheck(NonEmpty))  // Verify that at least one row exists


fun randomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
}

