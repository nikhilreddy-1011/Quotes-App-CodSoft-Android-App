pluginManagement {
    repositories {
        // Try Aliyun mirrors first (better connectivity in Asia)
        maven { 
            url = uri("https://maven.aliyun.com/repository/google")
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
        // Standard repos as fallback (for KSP and other plugins)
        google()
        mavenCentral()
        gradlePluginPortal()
        // JitPack as final fallback
        maven { url = uri("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        // Try Aliyun mirrors first (better connectivity in Asia)
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        // Standard repos as fallback
        google()
        mavenCentral()
        // JitPack as final fallback
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Quotes App"
include(":app")
