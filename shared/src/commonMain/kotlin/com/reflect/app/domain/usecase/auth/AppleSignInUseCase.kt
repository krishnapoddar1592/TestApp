package com.reflect.app.domain.usecase.auth


import com.reflect.app.domain.model.AuthResult
import com.reflect.app.domain.repository.UserRepository

class AppleSignInUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(idToken: String, nonce: String? = null): AuthResult {
        if (idToken.isBlank()) {
            return AuthResult.Error.Unknown("Invalid Apple credentials")
        }

        return userRepository.loginWithApple(idToken, nonce)
    }
}