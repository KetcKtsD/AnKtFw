@file:Suppress("UNUSED_VARIABLE")

subprojects {
    val minAndroidSdkVersion by extra { 24 }
    val targetAndroidSdkVersion by extra { 29 }
    val androidBuildToolVersion by extra { "29.0.2" }

    val libraryVersionCode by extra { 5 }
    val libraryVersionName by extra { "1.2.0" }
}
