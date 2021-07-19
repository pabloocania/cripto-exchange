buildscript {
    repositories {
        jcenter()
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
    }
/*
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.41")
    }
*/
}

plugins {
    kotlin("jvm") version "1.4.30-M1" apply false
}

allprojects {

    repositories {
        jcenter()
        mavenLocal()
        mavenCentral()
    }

//    tasks.named<Test>("test") {
//        useJUnitPlatform()
//        testLogging {
//            events("FAILED", "SKIPPED", "STANDARD_OUT", "STANDARD_ERROR")
//            showExceptions = true
//            showCauses = true
//            showStackTraces = true
//            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
//        }
//    }

}
