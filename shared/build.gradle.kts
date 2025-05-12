plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "ca.uwaterloo.cs346"
version = "1.3"

dependencies {
    implementation(libs.datetime)
    implementation(libs.json)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}