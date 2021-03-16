plugins {
    id ("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion (30)
    buildToolsVersion ("29.0.3")

    defaultConfig {
        minSdkVersion (24)
        targetSdkVersion (30)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility =  JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation( Dependencies.kotlin)
    testImplementation (Dependencies.junit)

    implementation(Dependencies.coroutine)

    implementation (Dependencies.hilt_android)
    kapt (Dependencies.hilt_compiler)
}