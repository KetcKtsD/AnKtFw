@file:Suppress("UNUSED_VARIABLE")

subprojects {
    val minAndroidSdkVersion by extra { 24 }
    val targetAndroidSdkVersion by extra { 29 }
    val androidBuildToolVersion by extra { "29.0.1" }

    val libraryVersionCode by extra { 1 }
    val libraryVersionName by extra { "1.3.41-29" }
}
