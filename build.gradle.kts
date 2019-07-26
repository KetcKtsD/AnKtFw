import org.jetbrains.kotlin.gradle.tasks.*

buildscript {
    val kotlinVersion = "1.3.41"
    apply(from = "android_sdk_settings.gradle.kts")
    apply(from = "notation_functions.gradle.kts")

    repositories {
        jcenter()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.6.0-alpha05")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

subprojects {
    repositories {
        jcenter()
        google()
    }

    group = "tech.ketc.anktfw"
    version = "0.1"

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf(
                        "-Xuse-experimental=kotlin.Experimental",
                        "-XXLanguage:+InlineClasses",
                        "-Xallow-result-return-type"
                )
            }
        }
    }
}

tasks {
    wrapper { gradleVersion = "5.5.1" }
}
