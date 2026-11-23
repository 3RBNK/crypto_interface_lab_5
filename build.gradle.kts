plugins {
    kotlin("jvm") version "2.4.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.bouncycastle:bcprov-jdk18on:1.87")
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}