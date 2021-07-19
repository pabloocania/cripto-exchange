import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

project.version = "1.1.3"

repositories {
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/kotlinx") }
}

val jacksonVersion: String by project
val shpGamesInterfaceVersion: String by project
val kotlinVersion: String by project

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.11.0")
    api("org.jetbrains.kotlin:kotlin-reflect:1.4.20")

    implementation(project(":exchange-api-core"))
    implementation(project(":exchange-entities"))

    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.3.1")
    testImplementation("io.kotest:kotest-runner-console-jvm:4.1.3.2")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.3.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events("FAILED", "SKIPPED", "STANDARD_OUT", "STANDARD_ERROR")
        showExceptions = true
        showCauses = true
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}
