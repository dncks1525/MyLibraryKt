plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId = "com.chani.mylibrarykt"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        resConfigs("en", "mdpi")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        named("debug") {
            manifestPlaceholders["enableCrashlytics"] = false
            manifestPlaceholders["alwaysUpdateBuildId"] = false
        }
    }

    buildFeatures {
        android.buildFeatures.dataBinding = true
        android.buildFeatures.viewBinding = true
    }
}

dependencies {
    common()
    lifecycle()
    retrofit()
    dagger()
    work()
    paging()
    glide()
    junit4()
    truth()
}