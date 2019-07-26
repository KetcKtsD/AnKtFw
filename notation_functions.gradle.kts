subprojects {
    //runtime
    val kotlinVersion by extra { "1.3.41" }
    val ktxCoroutinesVersion = "1.3.0-RC"
    val androidxLifecycleVersion = "2.0.0"
    val androidxInterpolatorVersion = "1.0.0"
    val ankoVersion = "0.10.8"
    //test
    val spek2Version = "2.0.5"
    val junitTestRunnerVersion = "1.0.0"
    //runtime
    extensions.notation("_kotlin") { "org.jetbrains.kotlin:kotlin-$it:$kotlinVersion" }
    extensions.notation("ktx_coroutines") { "org.jetbrains.kotlinx:kotlinx-coroutines-$it:$ktxCoroutinesVersion" }
    extensions.notation("androidx_lifecycle") { "androidx.lifecycle:lifecycle-$it:$androidxLifecycleVersion" }
    extensions.notation("androidx_interpolator") { "androidx.interpolator:interpolator:$androidxInterpolatorVersion" }
    extensions.notation("anko") { "org.jetbrains.anko:anko-$it:$ankoVersion" }
    //test
    extensions.notation("spek2") { "org.spekframework.spek2:spek-$it:$spek2Version" }
    extensions.notation("junit_test_runner") { "org.junit.platform:junit-platform-runner:$junitTestRunnerVersion" }
}


fun ExtensionContainer.notation(name: String, creator: (module: String) -> Any) {
    this.extraProperties[name] = creator
}
