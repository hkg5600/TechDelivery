plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "com.example.techdelivery"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        val options = this as org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
        options.jvmTarget = "1.8"
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

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":core"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dependencies.kotlin)
    implementation(Dependencies.core_ktx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.material)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.android_junit)
    androidTestImplementation(Dependencies.test_espresso)

    implementation(Dependencies.lifecycle_extension)

    implementation(Dependencies.activity_ktx)
    implementation(Dependencies.fragment_ktx)

    implementation(Dependencies.coroutine_viewmodel)
    implementation(Dependencies.coroutine_lifecycle)
    implementation(Dependencies.coroutine_livedata)
    implementation(Dependencies.coroutine)

    implementation(Dependencies.hilt_android)
    kapt(Dependencies.hilt_androidx_compiler)
    kapt(Dependencies.hilt_compiler)

    implementation(platform(Dependencies.firebase_bom))
    implementation(Dependencies.firebase_analytics)
    implementation(Dependencies.firebase_auth)

    implementation(Dependencies.navigation_fragment)
    implementation(Dependencies.navigation_ui)
}