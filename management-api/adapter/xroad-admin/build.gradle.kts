plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("jacoco")
}

group = "com.govstack.information_mediator.pubsub"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":management-api:domain"))

    implementation(project(":shared:commons"))
    implementation(project(":shared:domain-entity"))
    implementation("jakarta.annotation:jakarta.annotation-api:_")
    implementation("org.openapitools:jackson-databind-nullable:_")

    compileOnly("org.projectlombok:lombok:_")
    annotationProcessor("org.projectlombok:lombok:_")
    implementation("org.projectlombok:lombok:_")
    implementation("org.springframework.boot:spring-boot-starter:_")
    implementation(Spring.boot.web)

    implementation(Spring.boot.webflux)

    implementation("com.fasterxml.jackson.core:jackson-databind:_")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:_")

    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.80.Final:osx-aarch_64")

    implementation("com.google.code.findbugs:jsr305:_")
    testImplementation(Testing.junit4)


    testImplementation(Spring.boot.test)
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

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}
