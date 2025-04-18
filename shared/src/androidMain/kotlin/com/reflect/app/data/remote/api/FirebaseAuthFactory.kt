// shared/src/androidMain/kotlin/com/reflect/app/data/remote/api/FirebaseAuthFactory.kt
package com.reflect.app.data.remote.api

actual object FirebaseAuthFactory {
    actual fun createFirebaseAuth(): FirebaseAuthInterface = AndroidFirebaseAuth()
}

