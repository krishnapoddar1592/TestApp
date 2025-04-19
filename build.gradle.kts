plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinCocoapods).apply(false)
//    alias(libs.plugins.compose.compiler).apply(false)
    id("com.google.gms.google-services") version "4.4.2" apply false
}

//// Add this section
//allprojects {
//    configurations.all {
//        resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib:2.0.0") // Match your Kotlin version
//        resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib-common:2.0.0")
//        resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.0")
//        resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:2.0.0")
//    }
//}

