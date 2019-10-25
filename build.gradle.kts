import org.jetbrains.kotlin.gradle.tasks.*

buildscript {
    val kotlinVersion = "1.3.50"
    apply(from = "android_sdk_settings.gradle.kts")
    apply(from = "notation_functions.gradle.kts")
    apply(from = "task_utils.gradle.kts")

    repositories {
        jcenter()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0-alpha01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

subprojects {
    repositories {
        jcenter()
        google()
    }

    group = "tech.ketc.anktfw"

    tasks {
        withType<KotlinCompile> {
            sourceCompatibility = "1.8"
            targetCompatibility = "1.8"
            kotlinOptions {
                jvmTarget = "1.8"
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
    wrapper { gradleVersion = "5.6.2" }
}
