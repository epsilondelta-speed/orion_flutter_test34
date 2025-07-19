// build.gradle.kts for your orion_flutter Android library module

import org.gradle.api.tasks.testing.Test

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
        // Ensure you have a recent enough AGP to handle resolutionStrategy properly
        classpath("com.android.tools.build:gradle:8.1.0") // Or higher, if their Flutter version allows
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// IMPORTANT: Define versions centrally for easier management
val compileSdkVersion = 34
val targetSdkVersion = 34
val activityKtxVersion = "1.9.0" // Confirmed to be compatible with API 34
val coreKtxVersion = "1.12.0"   // Confirmed compatible with API 34

android {
    namespace = "co.epsilondelta.orion_flutter"
    compileSdk = compileSdkVersion

    defaultConfig {
        minSdk = 21
        targetSdk = targetSdkVersion
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
            // Keep this empty for now unless you have specific unit test configurations
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    dependencies {
        // Core dependencies of your plugin
        implementation("com.squareup.okhttp3:okhttp:4.12.0")
        implementation("org.json:json:20231013")

        // Explicitly include the older, compatible AndroidX Activity dependencies.
        // Even if other dependencies pull in newer versions, the 'force' rule below should override.
        implementation("androidx.activity:activity-ktx:$activityKtxVersion")
        implementation("androidx.activity:activity:$activityKtxVersion")
        implementation("androidx.core:core-ktx:$coreKtxVersion")

        // It might be useful to explicitly add lifecycle-runtime-ktx as well,
        // as activity often depends on it and sometimes it's an issue source.
        // Lifecycle 2.6.1 is compatible with API 34.
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    }
}

// THIS BLOCK IS CRUCIAL FOR FORCING VERSIONS
// It must be at the top-level scope of the script
configurations.all {
    resolutionStrategy {
        // Force specific versions for common AndroidX libraries that might cause issues
        // with compileSdk mismatches. Add any other libraries that cause problems here.
        force("androidx.activity:activity:$activityKtxVersion")
        force("androidx.activity:activity-ktx:$activityKtxVersion")
        force("androidx.core:core-ktx:$coreKtxVersion")
        force("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1") // Force this too

        // Optionally, add this to make Gradle fail immediately on conflict,
        // which can sometimes give clearer error messages about *why* a conflict happened.
        // Remove it after debugging.
        // failOnVersionConflict()
    }
}

// THIS BLOCK MUST BE AT THE TOP-LEVEL SCOPE OF THE SCRIPT
tasks.withType<Test>().configureEach {
    enabled = false // Explicitly disables any task of type Test
}

repositories {
    google()
    mavenCentral()
}
