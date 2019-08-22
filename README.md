# AnKtFw
Android周りの簡単なライブラリ

# usage

* build.gradle.kts

```build.gradle.kts
repository {
    jcenter()
    maven { setUrl("https://ketcktsd.github.io/maven") }
}

val anktfwVersion by { "1.1.0" }

dependencies {
    implementation("tech.ketc.anktfw:anktfw-arch:$anktfwVersion")
    implementation("tech.ketc.anktfw:anktfw-animation:$anktfwVersion")
    implementation("tech.ketc.anktfw:anktfw-anko:$anktfwVersion")
}
```
