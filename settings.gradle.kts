enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
//        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") // ✅ Required for Compose plugin

    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Reflect"
include(":androidApp")
include(":shared")
