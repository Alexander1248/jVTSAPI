plugins {
    id("java")
}

group = "ru.alexander"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")


    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    implementation("org.jetbrains:annotations:24.1.0")



    implementation("org.java-websocket:Java-WebSocket:1.5.6")
    testImplementation("org.slf4j:slf4j-nop:2.0.13")


    implementation("com.google.code.gson:gson:2.11.0")
}

tasks.test {
    useJUnitPlatform()
}