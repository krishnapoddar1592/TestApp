@file:Suppress("unused")

package com.reflect.app.data.remote.api

import platform.Foundation.NSNumber
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("FirebaseAuthBridge")
external class FirebaseAuthBridge {

    fun signInWithEmail(
        email: String,
        password: String,
        completion: (String?, String?, String?, NSNumber?, String?) -> Unit
    )

    fun signInWithGoogle(
        idToken: String,
        completion: (String?, String?, String?, NSNumber?, String?) -> Unit
    )

    fun signInWithApple(
        idToken: String,
        nonce: String?,
        completion: (String?, String?, String?, NSNumber?, String?) -> Unit
    )

    fun createUser(
        email: String,
        password: String,
        displayName: String?,
        completion: (String?, String?, String?, NSNumber?, String?) -> Unit
    )

    fun signOut()

    fun getCurrentUserId(): String?
    fun getCurrentUserEmail(): String?
    fun getCurrentUserDisplayName(): String?
    fun isCurrentUserEmailVerified(): NSNumber?
    fun deleteAccount(completion: (Boolean) -> Unit)
    fun sendPasswordResetEmail(email: String, completion: (Boolean) -> Unit)
}
