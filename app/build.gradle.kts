plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
    kotlin("android")
}

android {
    compileSdk = AppConfig.compileSdkVersion
    buildToolsVersion = AppConfig.buildToolVersion

    defaultConfig {
        applicationId = AppConfig.appId
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation

        buildConfigField("String", "BASE_URL", AppConfig.BASE_URL)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.stdLibraries)
    implementation(Dependencies.androidUILibraries)
    implementation(Dependencies.koinLibraries)
    implementation(Dependencies.archLibraries)
    implementation(Dependencies.glide)
    implementation(Dependencies.navLibraries)
    implementation(Dependencies.networkLibraries)
    implementation(Dependencies.reactiveLibraries)
    implementation(Dependencies.dbLibraries)
    implementation(Dependencies.timber)
    implementation(Dependencies.lottie)
    implementation(Dependencies.cameraLibraries)
    kapt(Dependencies.kaptLibraries)

    implementation(Dependencies.testLibraries)
    androidTestImplementation(Dependencies.androidTestLibraries)
}