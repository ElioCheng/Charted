plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "ca.uwaterloo.cs346"
version = "1.3"

dependencies {
    implementation(libs.clikt)
    implementation(libs.json)
    implementation(project(":shared"))

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "console.MainKt"
}