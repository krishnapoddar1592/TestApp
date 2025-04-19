package com.reflect.app.data.remote.api

import com.reflect.app.domain.model.SubscriptionType
import com.reflect.app.domain.model.User
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.datetime.Clock
import platform.Foundation.NSNumber
import kotlin.coroutines.resume

class IosFirebaseAuth : FirebaseAuthInterface {

    override suspend fun loginWithEmail(email: String, password: String): Result<User> {
        return suspendCancellableCoroutine { continuation ->
            FirebaseAuthBridge().signInWithEmail(
                email = email,
                password = password
            ) { userId, userEmail, displayName, isVerified, error ->
                if (error != null) {
                    continuation.resume(Result.failure(Exception(error)))
                } else if (userId != null) {
                    val user = User(
                        id = userId,
                        email = userEmail ?: "",
                        displayName = displayName,
                        isEmailVerified = isVerified?.toString()?.toBooleanStrictOrNull() ?: false,
                        subscriptionType = SubscriptionType.FREE,
                        createdAt = Clock.System.now().toEpochMilliseconds(),
                        lastLoginAt = Clock.System.now().toEpochMilliseconds()
                    )
                    continuation.resume(Result.success(user))
                } else {
                    continuation.resume(Result.failure(Exception("Unknown error")))
                }
            }
        }
    }

    override suspend fun loginWithGoogle(idToken: String): Result<User> {
        return suspendCancellableCoroutine { continuation ->
            FirebaseAuthBridge().signInWithGoogle(idToken) { userId, userEmail, displayName, isVerified, error ->
                if (error != null) {
                    continuation.resume(Result.failure(Exception(error)))
                } else if (userId != null) {
                    val user = User(
                        id = userId,
                        email = userEmail ?: "",
                        displayName = displayName,
                        isEmailVerified = isVerified?.toString()?.toBooleanStrictOrNull() ?: false,
                        subscriptionType = SubscriptionType.FREE,
                        createdAt = Clock.System.now().toEpochMilliseconds(),
                        lastLoginAt = Clock.System.now().toEpochMilliseconds()
                    )
                    continuation.resume(Result.success(user))
                } else {
                    continuation.resume(Result.failure(Exception("Unknown error")))
                }
            }
        }
    }

    override suspend fun loginWithApple(idToken: String, nonce: String?): Result<User> {
        return suspendCancellableCoroutine { continuation ->
            FirebaseAuthBridge().signInWithApple(
                idToken = idToken,
                nonce = nonce
            ) { userId, userEmail, displayName, isVerified, error ->
                if (error != null) {
                    continuation.resume(Result.failure(Exception(error)))
                } else if (userId != null) {
                    val user = User(
                        id = userId,
                        email = userEmail ?: "",
                        displayName = displayName,
                        isEmailVerified = isVerified?.toString()?.toBooleanStrictOrNull() ?: false,
                        subscriptionType = SubscriptionType.FREE,
                        createdAt = Clock.System.now().toEpochMilliseconds(),
                        lastLoginAt = Clock.System.now().toEpochMilliseconds()
                    )
                    continuation.resume(Result.success(user))
                } else {
                    continuation.resume(Result.failure(Exception("Unknown error")))
                }
            }
        }
    }

    override suspend fun register(email: String, password: String, displayName: String?): Result<User> {
        return suspendCancellableCoroutine { continuation ->
            FirebaseAuthBridge().createUser(
                email = email,
                password = password,
                displayName = displayName
            ) { userId, userEmail, userDisplayName, isVerified, error ->
                if (error != null) {
                    continuation.resume(Result.failure(Exception(error)))
                } else if (userId != null) {
                    val user = User(
                        id = userId,
                        email = userEmail ?: "",
                        displayName = userDisplayName,
                        isEmailVerified = isVerified?.toString()?.toBooleanStrictOrNull() ?: false,
                        subscriptionType = SubscriptionType.FREE,
                        createdAt = Clock.System.now().toEpochMilliseconds(),
                        lastLoginAt = Clock.System.now().toEpochMilliseconds()
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
            isEmailVerified = FirebaseAuthBridge().isCurrentUserEmailVerified()?.toString()?.toBooleanStrictOrNull() ?: false,
            subscriptionType = SubscriptionType.FREE,
            createdAt = Clock.System.now().toEpochMilliseconds(),
            lastLoginAt = Clock.System.now().toEpochMilliseconds()
        )
    }

    override suspend fun deleteAccount(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            FirebaseAuthBridge().deleteAccount { success ->
                continuation.resume(success)
            }
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            FirebaseAuthBridge().sendPasswordResetEmail(email) { success ->
                continuation.resume(success)
            }
        }
    }
}
