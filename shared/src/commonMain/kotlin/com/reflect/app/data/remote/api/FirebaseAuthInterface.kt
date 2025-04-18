package com.reflect.app.data.remote.api



import com.reflect.app.domain.model.User

// shared/src/commonMain/kotlin/com/reflect/app/data/remote/api/FirebaseAuthInterface.kt
interface FirebaseAuthInterface {
    suspend fun loginWithEmail(email: String, password: String): Result<User>
    suspend fun loginWithGoogle(idToken: String): Result<User>
    suspend fun loginWithApple(idToken: String, nonce: String?): Result<User>
    suspend fun register(email: String, password: String, displayName: String?): Result<User>
    suspend fun logout()
    suspend fun getCurrentUser(): User?
    suspend fun deleteAccount(): Boolean
    suspend fun sendPasswordResetEmail(email: String): Boolean
}