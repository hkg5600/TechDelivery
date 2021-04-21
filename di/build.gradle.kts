plugins {
    id ("com.android.library")
    kotlin("android")
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

    kotlinOptions {
        val options = this as org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
        options.jvmTarget = "1.8"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation( Dependencies.kotlin)
    implementation (Dependencies.core_ktx)
    implementation (Dependencies.appcompat)
    implementation (Dependencies.constraintlayout)
    testImplementation (Dependencies.junit)
    androidTestImplementation (Dependencies.android_junit)
    androidTestImplementation (Dependencies.test_espresso)

    implementation(Dependencies.coroutine)

    implementation (Dependencies.hilt_android)
    kapt (Dependencies.hilt_compiler)

    implementation (platform(Dependencies.firebase_bom))
    implementation(Dependencies.firebase_analytics)
    implementation(Dependencies.firebase_auth)

    implementation(Dependencies.gson_converter)
    implementation(Dependencies.retrofit2)

    implementation(Dependencies.datastore)
}