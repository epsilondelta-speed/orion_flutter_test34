// build.gradle.kts for your orion_flutter Android library module

// All imports should be at the very top of the file
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
    compileSdk = 34 // IMPORTANT: Downgraded to 34 to match client app

    defaultConfig {
        minSdk = 21
        targetSdk = 34
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
            // This block is for Android-specific unit test configurations.
            // Leaving it empty if you only want to disable the test task.
            // If you need to include Android resources for tests (even if disabled),
            // you might set: includeAndroidResources = false
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    dependencies {
        // Core dependencies of your plugin
        implementation("com.squareup.okhttp3:okhttp:4.12.0")
        implementation("org.json:json:20231013")

        // IMPORTANT: Downgraded AndroidX Activity dependencies
        // to versions compatible with compileSdk 34.
        implementation("androidx.activity:activity-ktx:1.9.0")
        implementation("androidx.activity:activity:1.9.0")

        // Explicitly adding core-ktx for compileSdk 34 compatibility
        implementation("androidx.core:core-ktx:1.12.0")
    }
}

// THIS BLOCK MUST BE AT THE TOP-LEVEL SCOPE OF THE SCRIPT
// It configures all tasks of type Test (which includes unit tests)
tasks.withType<Test>().configureEach {
    enabled = false // Explicitly disables any task of type Test
}


// This `repositories` block at the root level of the script is generally
// not strictly needed for a library module if `allprojects` is already defined
// or if the root project's `settings.gradle.kts` handles repositories.
// However, it doesn't cause harm.
repositories {
    google()
    mavenCentral()
}
