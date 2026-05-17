plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.webtile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.webtile"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.wear.tiles:tiles:1.4.0-alpha04")
    implementation("androidx.wear.tiles:tiles-material:1.4.0-alpha04")
    implementation("androidx.wear.tiles:tiles-proto:1.4.0-alpha04")
    implementation("com.google.guava:guava:31.1-android")
}
