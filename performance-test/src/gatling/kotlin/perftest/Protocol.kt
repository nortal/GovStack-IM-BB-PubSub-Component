package perftest

import io.gatling.javaapi.http.HttpDsl
import ru.tinkoff.load.javaapi.JdbcDsl.DB
import ru.tinkoff.load.javaapi.protocol.JdbcProtocolBuilder

object Protocol {
    val http = HttpDsl.http.baseUrl(Configuration.get(BASE_URL)).contentTypeHeader("application/json")

    val database: JdbcProtocolBuilder = DB()
            .url(Configuration.get(DB_JDBC_URL))
            .username(Configuration.get(DB_USER))
            .password(Configuration.get(DB_PASS))
            .maximumPoolSize(20)
            .protocolBuilder()

}
