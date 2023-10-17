import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Property

plugins {
    id("java")
    id("nu.studer.jooq")
}

group = "com.govstack.information_mediator.pubsub"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared:commons"))

    runtimeOnly("org.postgresql:postgresql:_")
    jooqGenerator("org.postgresql:postgresql:_")

    compileOnly("org.projectlombok:lombok:_")
    annotationProcessor("org.projectlombok:lombok:_")
    implementation ("org.projectlombok:lombok:_")

    implementation("org.apache.commons:commons-lang3:_")
    implementation("com.networknt:json-schema-validator:_")

    testImplementation(Testing.assertj.core)
    testImplementation(Testing.mockito.core)
    testImplementation(Testing.junit.jupiter.api)
    testImplementation(Testing.junit.jupiter.params)
    testRuntimeOnly(Testing.junit.jupiter.engine)

    testImplementation("org.json:json:_")
    testImplementation("org.reflections:reflections:_")
    testRuntimeOnly("org.reflections:reflections:_")
}

tasks.test {
    useJUnitPlatform()
}

rootProject.extra["jooq.version"] = "3.18.4"

jooq {
    version.set(rootProject.extra["jooq.version"] as String)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(false)

            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = project.findProperty("jooqDbUrl") as String? ?: "jdbc:postgresql://localhost:5432/im_msg_db"
                    user = project.findProperty("jooqDbUser") as String? ?: "im_msg_usr"
                    password = project.findProperty("jooqDbPassword") as String? ?: "im_msg_pass"
                }
                generator.apply {
                    name = "org.jooq.codegen.JavaGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        isIncludeRoutines = false
                        excludes = "DATABASECHANGELOG.* | PG_STAT_STATEMENTS"
                        inputSchema = project.findProperty("jooqDbSchema") as String? ?: "im_msg"
                        forcedTypes.addAll(arrayOf(
                                ForcedType()
                                        .withName("Instant")
                                        .withIncludeTypes("timestamp\\ with\\ time\\ zone")
                        ).toList())
                        // The default name case for unquoted objects:
                        //
                        // - as_is: unquoted object names are kept unquoted
                        // - upper: unquoted object names are turned into upper case (most databases)
                        // - lower: unquoted object names are turned into lower case (e.g. PostgreSQL)
                        withProperties(
                                Property().apply {
                                    key = "defaultNameCase"
                                    value = "as_is"
                                }
                        )
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "com.govstack.information_mediator.pubsub.shared.jooq"
                        directory = "src/generated/jooq"
                        encoding = "UTF-8"
                        isClean = true
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}
