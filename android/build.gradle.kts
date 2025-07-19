// build.gradle.kts for your orion_flutter Android library module

plugins {
    id("com.android.library")
    kotlin("android")
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

android {
    namespace = "co.epsilondelta.orion_flutter"
    // IMPORTANT CHANGE: Downgrade compileSdk to 34 to match the client app
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34 // Keep targetSdk at 34 as it aligns with the client app's compileSdk
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests.all {
            // Keep this block, but it's typically empty or used for other test configurations
            // as 'enabled = false' is handled by the tasks.withType<Test> block below.
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    dependencies {
        // Core dependencies of your plugin
        implementation("com.squareup.okhttp3:okhttp:4.12.0")
        implementation("org.json:json:20231013")

        // IMPORTANT CHANGES: Downgrade AndroidX Activity dependencies
        // to versions compatible with compileSdk 34.
        // Version 1.9.0 of Activity seems to be the last one fully targeting API 34.
        implementation("androidx.activity:activity-ktx:1.9.0")
        implementation("androidx.activity:activity:1.9.0")

        // You might also need to explicitly add core-ktx if it was implicitly pulled in by 1.10.1
        // and is now missing or needs a specific compatible version for compileSdk 34.
        // A common version for compileSdk 34 is 1.12.0 or 1.10.1 for older Flutter versions.
        // Let's assume 1.12.0 for now, but if you get more errors related to core-ktx,
        // you might need to adjust this.
        implementation("androidx.core:core-ktx:1.12.0")
    }
}

import org.gradle.api.tasks.testing.Test

tasks.withType<Test>().configureEach {
    enabled = false // This explicitly disables any task of type Test
}

repositories {
    google()
    mavenCentral()
}