plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
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

        testInstrumentationRunner = "com.chani.mylibrarykt.MyTestRunner"
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        named("debug") {
            isMinifyEnabled = false
            firebaseCrashlytics {
                mappingFileUploadEnabled = false
            }
        }
    }

    buildFeatures {
        android.buildFeatures.dataBinding = true
        android.buildFeatures.viewBinding = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    kapt {
        correctErrorTypes = true
    }

    hilt {
        enableTransformForLocalTests = true
    }
}

dependencies {
    common()
    firebase()
    lifecycle()
    retrofit()
    hilt()
    work()
    room()
    paging()
    glide()
    robolectric()
    truth()
}