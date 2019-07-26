@file:Suppress("PropertyName")

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-kapt")
    maven
}

val libraryVersionName: String by extra

android {
    val minAndroidSdkVersion: Int by extra
    val targetAndroidSdkVersion: Int by extra
    val androidBuildToolVersion: String by extra
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
val ktx_coroutines: (String) -> Any by extra
val androidx_lifecycle: (String) -> Any by extra
val spek2: (String) -> Any by extra
val junit_test_runner: (String) -> Any by extra
val junit_test_runner_notation get() = junit_test_runner("")

dependencies {
    implementation(_kotlin("stdlib-jdk8"))
    implementation(_kotlin("reflect"))
    implementation(ktx_coroutines("jdk8"))
    implementation(ktx_coroutines("android"))
    implementation(androidx_lifecycle("common-java8"))
    implementation(androidx_lifecycle("runtime"))
    kapt(androidx_lifecycle("compiler"))

    //test
    testImplementation(_kotlin("test"))
    testImplementation(spek2("dsl-jvm"))
    testImplementation(spek2("runner-junit5"))
    testImplementation(junit_test_runner_notation)
}

val uploadArchives: TaskContainerScope.(String) -> TaskProvider<Upload> by extra

tasks {
    uploadArchives("arch")
}
