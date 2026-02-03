pluginManagement {
    repositories {
        // Try multiple mirrors to avoid connection issues
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
        google()
        mavenCentral()
        gradlePluginPortal()
        // JitPack as fallback
        maven { url = uri("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    // Allow project repos to use alternative mirrors
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        // Try Aliyun mirrors first (faster and more reliable in Asia)
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        
        // Original repos as fallback
        google()
        mavenCentral()
        
        // JitPack as additional fallback
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Quotes App"
include(":app")
