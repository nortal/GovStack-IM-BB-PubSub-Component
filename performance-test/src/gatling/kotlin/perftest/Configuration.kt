package perftest

import java.util.Properties

const val BASE_URL = "base-url"

const val DB_JDBC_URL = "db-jdbc-url"
const val DB_USER = "db-user"
const val DB_PASS = "db-pass"

object Configuration {

    private val properties = Properties()

    init {
        properties.load(javaClass.classLoader.getResourceAsStream("configuration.properties"))
    }

    fun get(key: String): String {
        return properties.getProperty(key)
    }
}
