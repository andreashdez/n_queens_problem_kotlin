plugins {
    kotlin("jvm") version "2.0.0"
    id("application")
}

group = "com.andreashdez"
version = "1.0-SNAPSHOT"

application {
    applicationDefaultJvmArgs = listOf("-Dgreeting.language=en")
    mainClass = "com.andreashdez.Main.kt"
}

repositories {
    mavenCentral()
}

val kotlinLoggingJvmVersion = "3.0.5"
val logbackClassicVersion = "1.5.6"

dependencies {
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")
    implementation("ch.qos.logback:logback-classic:$logbackClassicVersion")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}
