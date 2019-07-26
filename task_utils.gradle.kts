subprojects {
    //uploadArchives
    val uploadArchives: TaskContainerScope.(String) -> TaskProvider<Upload> = { module ->
        val libraryVersionName: String by extra
        named<Upload>("uploadArchives") {
            val mavenConvention = org.gradle.api.internal.plugins.DslObject(repositories)
                    .convention.getPlugin(MavenRepositoryHandlerConvention::class.java)
            val mavenDeployer = mavenConvention.mavenDeployer()
            val repo = File(rootDir, "repository")
            repo.takeUnless(File::exists)?.run(File::mkdir)
            repositories {
                mavenDeployer.run {
                    javaClass.getMethod("repository", Map::class.java)
                            .invoke(mavenDeployer, mapOf("url" to "file://${repo.absolutePath}"))
                    pom {
                        version = libraryVersionName
                        groupId = "tech.ketc.anktfw"
                        artifactId = "anktfw-$module"
                    }
                }
            }
        }
    }
    extensions.extraProperties["uploadArchives"] = uploadArchives
}