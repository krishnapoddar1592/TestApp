

// shared/src/iosMain/kotlin/com/reflect/app/data/remote/api/IosFirebaseAuth.kt
package com.reflect.app.data.remote.api

import com.reflect.app.domain.model.SubscriptionType
import com.reflect.app.domain.model.User
import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
class IosFirebaseAuth : FirebaseAuthInterface {

    override suspend fun loginWithEmail(email: String, password: String): Result<User> {
        return suspendCoroutine { continuation ->
            FirebaseAuthBridge().signInWithEmail(
                email = email,
                password = password
            ) { userId, userEmail, displayName, error ->
                if (error != null) {
                    continuation.resume(Result.failure(Exception(error)))
                } else if (userId != null) {
                    val user = User(
                        id = userId,
                        email = userEmail ?: "",
                        displayName = displayName,
                        isEmailVerified = false, // We'll get this from the Swift bridge
                        subscriptionType = SubscriptionType.FREE,
                        createdAt = System.currentTimeMillis(),
                        lastLoginAt = System.currentTimeMillis()
                    )
                    continuation.resume(Result.success(user))
                } else {
                    continuation.resume(Result.failure(Exception("Unknown error")))
                }
            }
        }
    }

    override suspend fun loginWithGoogle(idToken: String): Result<User> {
        return suspendCoroutine { continuation ->
            FirebaseAuthBridge().signInWithGoogle(idToken) { userId, userEmail, displayName, error ->
                if (error != null) {
                    continuation.resume(Result.failure(Exception(error)))
                } else if (userId != null) {
                    val user = User(
                        id = userId,
                        email = userEmail ?: "",
                        displayName = displayName,
                        isEmailVerified = false,
                        subscriptionType = SubscriptionType.FREE,
                        createdAt = System.currentTimeMillis(),
                        lastLoginAt = System.currentTimeMillis()
                    )
                    continuation.resume(Result.success(user))
                } else {
                    continuation.resume(Result.failure(Exception("Unknown error")))
                }
            }
        }
    }

    override suspend fun loginWithApple(idToken: String, nonce: String?): Result<User> {
        return suspendCoroutine { continuation ->
            FirebaseAuthBridge().signInWithApple(
                idToken = idToken,
                nonce = nonce
            ) { userId, userEmail, displayName, error ->
                if (error != null) {
                    continuation.resume(Result.failure(Exception(error)))
                } else if (userId != null) {
                    val user = User(
                        id = userId,
                        email = userEmail ?: "",
                        displayName = displayName,
                        isEmailVerified = false,
                        subscriptionType = SubscriptionType.FREE,
                        createdAt = System.currentTimeMillis(),
                        lastLoginAt = System.currentTimeMillis()
                    )
                    continuation.resume(Result.success(user))
                } else {
                    continuation.resume(Result.failure(Exception("Unknown error")))
                }
            }
        }
    }

    override suspend fun register(email: String, password: String, displayName: String?): Result<User> {
        return suspendCoroutine { continuation ->
            FirebaseAuthBridge().createUser(
                email = email,
                password = password,
                displayName = displayName
            ) { userId, userEmail, userDisplayName, error ->
                if (error != null) {
                    continuation.resume(Result.failure(Exception(error)))
                } else if (userId != null) {
                    val user = User(
                        id = userId,
                        email = userEmail ?: "",
                        displayName = userDisplayName,
                        isEmailVerified = false,
                        subscriptionType = SubscriptionType.FREE,
                        createdAt = System.currentTimeMillis(),
                        lastLoginAt = System.currentTimeMillis()
                    )
                    continuation.resume(Result.success(user))
                } else {
                    continuation.resume(Result.failure(Exception("Registration failed")))
                }
            }
        }
    }

    override suspend fun logout() {
        FirebaseAuthBridge().signOut()
    }

    override suspend fun getCurrentUser(): User? {
        val userId = FirebaseAuthBridge().getCurrentUserId() ?: return null
        val email = FirebaseAuthBridge().getCurrentUserEmail() ?: ""
        val displayName = FirebaseAuthBridge().getCurrentUserDisplayName()

        return User(
            id = userId,
            email = email,
            displayName = displayName,
            isEmailVerified = FirebaseAuthBridge().isCurrentUserEmailVerified(),
            subscriptionType = SubscriptionType.FREE,
            createdAt = 0,
            lastLoginAt = 0
        )
    }

    override suspend fun deleteAccount(): Boolean {
        return suspendCoroutine { continuation ->
            FirebaseAuthBridge().deleteAccount { success ->
                continuation.resume(success)
            }
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Boolean {
        return suspendCoroutine { continuation ->
            FirebaseAuthBridge().sendPasswordResetEmail(email) { success ->
                continuation.resume(success)
            }
        }
    }
}