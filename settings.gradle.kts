pluginManagement {
    // Uncomment only to test the plugin
    // Make sure to include in iconset-generator-plugin module the repository block with google and mavenCentral()
    // and remove it after testing
    // includeBuild("iconset-generator-plugin")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "IconSetGenerator"
include(":app")
// Comment only to test the plugin
include(":iconset-generator-plugin")
