import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

project.version = "1.1.3"

repositories {
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/kotlinx") }
    maven { url = uri("https://jitpack.io") }
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
    implementation ("org.web3j:core:4.5.0")
    api("org.jetbrains.kotlin:kotlin-reflect:1.4.20")

    implementation(project(":blockchain-provider"))
    implementation(project(":blockchain-client"))
    implementation(project(":exchange-entities"))
    implementation(project(":contracts"))

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
//task adminNpmInstall(type: NpmTask) {
//    workingDir = file("src/main/front/")
//    args = ['install']
//}

//tasks.named<Test>("test") {
//    useJUnitPlatform()
//    testLogging {
//        events("FAILED", "SKIPPED", "STANDARD_OUT", "STANDARD_ERROR")
//        showExceptions = true
//        showCauses = true
//        showStackTraces = true
//        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
//    }
//}
