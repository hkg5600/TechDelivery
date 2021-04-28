// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.4.31")
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:4.1.3")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinVersion}")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.34.1-beta")
        classpath ("com.google.gms:google-services:4.3.5")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}

