@file:Suppress("PropertyName")

plugins {
    id("com.android.library")
    kotlin("android")
    maven
}

android {
    val minAndroidSdkVersion: Int by extra
    val targetAndroidSdkVersion: Int by extra
    val androidBuildToolVersion: String by extra
    val libraryVersionName: String by extra
    val libraryVersionCode: Int by extra

    compileSdkVersion(targetAndroidSdkVersion)

    buildToolsVersion = androidBuildToolVersion

    defaultConfig {
        minSdkVersion(minAndroidSdkVersion)
        targetSdkVersion(targetAndroidSdkVersion)
        versionCode = libraryVersionCode
        versionName = libraryVersionName
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), file("proguard-rules.pro"))
        }
    }
    sourceSets {
        getByName("main") {
            java.setSrcDirs(setOf("src/main/kotlin"))
        }
        getByName("test") {
            java.setSrcDirs(setOf("src/test/kotlin", "src/androidTest/kotlin"))
        }
    }
}
val _kotlin: (String) -> Any by extra
val androidx_interpolator: (String) -> Any by extra
val androidx_interpolator_notation get() = androidx_interpolator("")

dependencies {
    implementation(_kotlin("stdlib-jdk8"))
    implementation(androidx_interpolator_notation)
}

val uploadArchives: TaskContainerScope.(String) -> TaskProvider<Upload> by extra

tasks {
    uploadArchives("animation")
}
