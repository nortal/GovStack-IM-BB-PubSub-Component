import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

plugins {
    id("org.liquibase.gradle")
    id("com.bmuschko.docker-remote-api")
}

apply(plugin = "org.liquibase.gradle")

group = "com.govstack.information_mediator.pubsub"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    liquibaseRuntime("org.liquibase:liquibase-core:_")
    liquibaseRuntime("javax.xml.bind:jaxb-api:_")
    liquibaseRuntime("org.postgresql:postgresql:_")
    liquibaseRuntime("info.picocli:picocli:_")
    liquibaseRuntime("org.yaml:snakeyaml:_")
}

liquibase {
    activities.register("main") {
        arguments = mapOf(
                "changeLogFile" to "/db.changelog-master.yaml",
                "classpath" to "$projectDir/changelog",
                "url" to (project.findProperty("liquibaseJdbcUrl") ?: "jdbc:postgresql://localhost:5432/im_msg_db?currentSchema=im_msg"),
                "username" to (project.findProperty("liquibaseUser") ?: "im_msg_usr"),
                "password" to (project.findProperty("liquibasePassword") ?: "im_msg_pass"),
                "logLevel" to "info"
        )
    }
}
tasks.register("printVersion") {
    println(project.version)
}

tasks.register("buildImage", DockerBuildImage::class) {
    val imageName = properties["imageName"].toString()
    images.add(imageName)
    inputDir.set(file("."))
}
