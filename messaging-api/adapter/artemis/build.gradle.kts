plugins {
    id("java")
    id("org.kordamp.gradle.jandex")
    id("jacoco")
}

group = "com.govstack.information_mediator.pubsub"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":messaging-api:domain"))
    implementation(project(":shared:commons"))
    implementation(project(":shared:domain-entity"))

    compileOnly("org.projectlombok:lombok:_")
    annotationProcessor("org.projectlombok:lombok:_")

    implementation("io.quarkus:quarkus-core:_")
    implementation("io.quarkus:quarkus-jackson:_")
    implementation("io.quarkus:quarkus-vertx:_")
    implementation("io.quarkus:quarkus-scheduler:_")
    implementation("io.quarkus:quarkus-narayana-jta:_")
    implementation("io.quarkiverse.messaginghub:quarkus-pooled-jms:_")
    implementation("org.amqphub.quarkus:quarkus-qpid-jms:_")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson:_")
    implementation("io.quarkus:quarkus-smallrye-reactive-messaging-amqp:_")

    testImplementation("org.hamcrest:java-hamcrest:_")
    testImplementation(Testing.assertj.core)
    testImplementation(Testing.mockito.core)
    testImplementation(Testing.mockito.junitJupiter)
    testImplementation(Testing.junit.jupiter.engine)
    testImplementation(Testing.junit.jupiter.params)
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
