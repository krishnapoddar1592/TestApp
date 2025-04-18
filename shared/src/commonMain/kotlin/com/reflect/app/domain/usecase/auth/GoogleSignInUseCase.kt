package com.reflect.app.domain.usecase.auth


import com.reflect.app.domain.model.AuthResult
import com.reflect.app.domain.repository.UserRepository

class GoogleSignInUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(idToken: String): AuthResult {
        if (idToken.isBlank()) {
            return AuthResult.Error.Unknown("Invalid Google credentials")
        }

        return userRepository.loginWithGoogle(idToken)
    }
}