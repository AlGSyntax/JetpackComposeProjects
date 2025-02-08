plugins {
    // Apply the Android Application plugin. This plugin is essential for building Android applications.
    alias(libs.plugins.android.application)

    // Apply the Kotlin Android plugin. This plugin enables Kotlin support for Android development.
    alias(libs.plugins.jetbrains.kotlin.android)

    // Apply the Compose Compiler plugin. This plugin is necessary for compiling Jetpack Compose code.
    alias(libs.plugins.compose.compiler)

    // Apply the Kotlin Symbol Processing (KSP) plugin. KSP is used for annotation processing,
    // which is required by libraries like Room.
    alias(libs.plugins.ksp)
}

// Configure the Android build settings.
android {
    // Set the namespace for the application. This is used to generate R classes and other resources.
    namespace = "com.algsyntax.todojetpackcompose"

    // Set the compile SDK version. This is the API level against which the app will be compiled.
    compileSdk = 35

    // Configure the default application settings.
    defaultConfig {
        // Set the application ID. This is the unique identifier for the app.
        applicationId = "com.algsyntax.todojetpackcompose"

        // Set the minimum SDK version supported by the app.
        minSdk = 34

        // Set the target SDK version. This indicates the API level the app is designed to run on.
        targetSdk = 35

        // Set the version code of the app. This is an integer used for versioning.
        versionCode = 1

        // Set the version name of the app. This is a user-readable version string.
        versionName = "1.0"

        // Set the test instrumentation runner. This is used for running Android tests.
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Configure vector drawables.
        vectorDrawables {
            // Use the support library for vector drawables.
            useSupportLibrary = true
        }

        // Configure the Native Development Kit (NDK).
        ndk {
            // Specify the Application Binary Interfaces (ABIs) supported by the app.
            // This ensures that the app can run on different CPU architectures.
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }
    }

    // Configure the build types.
    buildTypes {
        // Configure the release build type.
        release {
            // Disable code minification for the release build.
            isMinifyEnabled = false

            // Specify the ProGuard rules files to use for code optimization.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Configure the compilation options.
    compileOptions {
        // Set the source compatibility to Java 19.
        sourceCompatibility = JavaVersion.VERSION_19

        // Set the target compatibility to Java 19.
        targetCompatibility = JavaVersion.VERSION_19
    }

    // Configure the Kotlin options.
    kotlinOptions {
        // Set the JVM target to Java 19.
        jvmTarget = "19"
    }

    // Configure the build features.
    buildFeatures {
        // Enable Jetpack Compose support.
        compose = true
    }

    // Configure the packaging options.
    packaging {
        resources {
            // Exclude certain files from the packaged APK.
            // This is often done to avoid conflicts with duplicate files from different libraries.
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Configure the dependencies for the project.
dependencies {
    // Implementation dependencies are visible to the app and any modules that depend on it.
    // Core Kotlin extensions for Android.
    implementation(libs.androidx.core.ktx)

    // Runtime components for the Android Lifecycle library.
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // ViewModel components for the Android Lifecycle library.
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // LiveData components for the Android Lifecycle library.
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // ViewModel support for Jetpack Compose.
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Activity Compose support for Jetpack Compose.
    implementation(libs.androidx.activity.compose)

    // The Bill of Materials (BOM) for Jetpack Compose.
    // This helps manage the versions of multiple Compose libraries.
    implementation(platform(libs.androidx.compose.bom))

    // Navigation Compose support for Jetpack Compose.
    implementation(libs.androidx.navigation.compose)

    // Core UI components for Jetpack Compose.
    implementation(libs.androidx.ui)

    // Graphics components for Jetpack Compose.
    implementation(libs.androidx.ui.graphics)

    // Tooling preview components for Jetpack Compose.
    implementation(libs.androidx.ui.tooling.preview)

    // Material 3 components for Jetpack Compose.
    implementation(libs.androidx.material3)

    // LiveData support for Jetpack Compose.
    implementation(libs.androidx.runtime.livedata)

    // Runtime components for the Android Lifecycle library.
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Core Kotlin coroutines library.
    implementation(libs.kotlinx.coroutines.core)

    // Android-specific Kotlin coroutines library.
    implementation(libs.kotlinx.coroutines.android)

    // Room database runtime library.
    implementation(libs.androidx.room.runtime)

    // SQLCipher for Android.
    implementation(libs.sqlcipher)

    // AndroidX SQLite library.
    implementation(libs.androidx.sqlite)

    // Security Crypto library for EncryptedSharedPreferences.
    implementation(libs.security.crypto)

    // KSP annotation processor for Room.
    ksp(libs.androidx.room.compiler)

    // Kotlin extensions for Room.
    implementation(libs.androidx.room.ktx)

    // Test dependencies are only visible to the test code.
    // JUnit for unit tests.
    testImplementation(libs.junit)

    // Android test dependencies are only visible to the Android test code.
    // JUnit for Android tests.
    androidTestImplementation(libs.androidx.junit)

    // Espresso Core for UI tests.
    androidTestImplementation(libs.androidx.espresso.core)

    // The Bill of Materials (BOM) for Jetpack Compose for Android tests.
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // JUnit 4 for UI tests.
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug dependencies are only visible to the debug build.
    // Tooling components for Jetpack Compose.
    debugImplementation(libs.androidx.ui.tooling)

    // Test manifest for UI tests.
    debugImplementation(libs.androidx.ui.test.manifest)
}