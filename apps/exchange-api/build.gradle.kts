import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
    id("com.github.johnrengelman.shadow") version "4.0.3"
}

val artifactoryUsername: String by project
val artifactoryPassword: String by project

repositories {
    jcenter()
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

val sentryLogback: String by project
val kotlinVersion: String by project
val kotlinxCoroutinesVersion: String by project
//val kotlinxCoroutinesCommonsVersion: String by project
val ktorVersion: String by project
val sl4jVersion: String by project
val hopliteVersion: String by project
val jacksonVersion: String by project

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
    //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$kotlinxCoroutinesCommonsVersion")

    // ktor
    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
    implementation("io.ktor:ktor-metrics-micrometer:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation ("org.web3j:core:4.5.0")

    implementation("org.slf4j:slf4j-api:$sl4jVersion")
    implementation("org.slf4j:slf4j-simple:$sl4jVersion")
    implementation("com.sksamuel.hoplite:hoplite-core:$hopliteVersion")
    implementation("com.sksamuel.hoplite:hoplite-json:$hopliteVersion")

    implementation(project(":exchange-entities"))
    implementation(project(":exchange-api-core"))
    implementation(project(":blockchain-provider"))
    implementation(project(":blockchain-client"))
    implementation(project(":eth-blockchain-client"))

    //test
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
    exclude("META-INF/*.DSA")
    exclude("META-INF/*.RSA")
    manifest.attributes["Main-Class"] = application.mainClassName
}

val run by tasks.getting(JavaExec::class) {
    loadEnvFile("default.env")
    loadEnvFile("local.env")
}

fun JavaExec.loadEnvFile(fileName: String) {
    val file = project.projectDir.resolve(fileName)
    if (file.exists()) {
        file.readLines().filter { !it.startsWith("#")}.forEach {
            val split = it.split("=")
            val key = split[0]
            val value = split.drop(1).joinToString("=")
            environment(key, value)
        }
    }
}

