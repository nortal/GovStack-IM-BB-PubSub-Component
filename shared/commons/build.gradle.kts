plugins {
    id("java")
}

group = "com.govstack.information_mediator.pubsub"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:_")
    annotationProcessor("org.projectlombok:lombok:_")
    implementation ("org.projectlombok:lombok:_")
    implementation("io.swagger.core.v3:swagger-annotations:_")

    implementation("com.networknt:json-schema-validator:_")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:_")
    implementation("com.fasterxml.uuid:java-uuid-generator:_")
}
