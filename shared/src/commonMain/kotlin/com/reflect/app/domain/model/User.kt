// In shared/src/commonMain/kotlin/com/reflect/app/domain/model/User.kt
package com.reflect.app.domain.model

import kotlinx.serialization.Serializable
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@Serializable
@OptIn(ExperimentalObjCName::class)
@ObjCName("User","User")
data class User(
    val id: String,
    val email: String,
    val displayName: String? = null,
    val isEmailVerified: Boolean = false,
    val subscriptionType: SubscriptionType = SubscriptionType.FREE,
    val createdAt: Long = 0,
    val lastLoginAt: Long = 0
)

// Make sure SubscriptionType is also serializable
@Serializable
@OptIn(ExperimentalObjCName::class)
@ObjCName("SubscriptionType","SubscriptionType")
enum class SubscriptionType {
    FREE, TRIAL, PREMIUM
}