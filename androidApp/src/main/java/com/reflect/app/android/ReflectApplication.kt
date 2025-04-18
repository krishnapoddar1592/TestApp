// File: androidApp/src/main/java/com/reflect/app/android/ReflectApplication.kt
package com.reflect.app.android

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.reflect.app.di.androidModule
import com.reflect.app.di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ReflectApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Add logging to verify this method is called
        Log.d("ReflectApp", "Application onCreate called")

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Initialize Koin with more verbose logging
        startKoin {
            androidLogger(Level.DEBUG) // More verbose logging
            androidContext(this@ReflectApplication)
            modules(commonModule, androidModule)
        }

        Log.d("ReflectApp", "Koin initialization completed")
    }
}