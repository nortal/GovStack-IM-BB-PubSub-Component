plugins {
    id("java")
    id("org.kordamp.gradle.jandex")
    id("jacoco")
}

group = "com.govstack.information_mediator.pubsub"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared:commons"))
    implementation(project(":shared:domain-entity"))
    implementation(project(":shared:jooq"))

    implementation(project(":messaging-api:domain"))

    implementation("org.jooq:jooq:_")
    implementation("org.apache.commons:commons-lang3:_")
    implementation("com.networknt:json-schema-validator:_")

    compileOnly("org.projectlombok:lombok:_")
    annotationProcessor("org.projectlombok:lombok:_")
    implementation ("org.projectlombok:lombok:_")

    // Quarkus extensions
    implementation("io.quarkiverse.jooq:quarkus-jooq:_")
    implementation("io.quarkus:quarkus-jdbc-postgresql:_")

    /// Quarkus dependencies
    implementation("io.quarkus:quarkus-core:_")
    implementation("io.quarkus:quarkus-jackson:_")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson:_")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        xml.outputLocation.set(file("build/reports/jacoco/test/jacocoTestReport.xml"))
    }
}

tasks.named("compileTestJava").configure {
    dependsOn("jandex")
}
