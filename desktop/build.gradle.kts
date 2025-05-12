import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

group = "ca.uwaterloo.cs346"
version = "1.3"

dependencies {
    implementation(libs.datetime)
    implementation(compose.desktop.currentOs)
    implementation(project(":shared"))
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

compose.desktop {
    application {
        mainClass = "desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ca.uwaterloo.cs346"
            packageVersion = "1.3.0"
        }
    }
}