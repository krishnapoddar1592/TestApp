import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization") version "2.0.0"

}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    cocoapods {
        summary = "Shared code for Reflect App"
        homepage = "https://…"
        // ← add this:
        version = "1.0.0"
        ios.deploymentTarget = "14.0"

        framework {
            baseName = "shared"
            isStatic = false
        }
        // (you can re‑add Firebase pods here if you need cinterop – see below)
        pod("Firebase/Auth")
        pod("Firebase/Firestore")
    }

    
    sourceSets {
        androidMain.dependencies {
            // Firebase
            implementation("com.google.firebase:firebase-auth-ktx:22.3.0")
            implementation("com.google.android.gms:play-services-auth:20.7.0")

            // Koin for Android
            implementation("io.insert-koin:koin-android:3.5.0")
        }
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

                // Koin for DI
                implementation("io.insert-koin:koin-core:3.5.0")

                // Serialization
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

                // DateTime
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
            }


        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        // Create an iosMain and have each target’s main depend on it:
//        val iosMain by creating {
//            dependsOn(commonMain)
//        }
//        val iosX64Main by getting     { dependsOn(iosMain) }
//        val iosArm64Main by getting    { dependsOn(iosMain) }
//        val iosSimulatorArm64Main by getting { dependsOn(iosMain) }
        // 3) Create a shared iOS‐only “umbrella” set
        val iosMain by creating {
            dependsOn(commonMain)        // ← this works because you’re in sourceSets { … }
        }
        val iosTest by creating {
            dependsOn(commonTest)
        }

        // 4) Hook each platform’s Main/Test into iosMain/Test
        val iosX64Main by getting {
            dependsOn(iosMain)
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }

        val iosX64Test by getting {
            dependsOn(iosTest)
        }
        val iosArm64Test by getting {
            dependsOn(iosTest)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }
    }
}

android {
    namespace = "com.reflect.app"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
dependencies {
    implementation(libs.androidx.compose.material.core)
    implementation(libs.androidx.lifecycle.viewmodel.android)
}
kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}
// At the bottom of the file, add:
kotlin.targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java) {
    binaries.all {
        // Export all Kotlin declarations by default
        freeCompilerArgs += "-Xexport-kdoc"
    }
}
