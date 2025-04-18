package com.reflect.app.domain.usecase.auth

import com.reflect.app.domain.model.AuthResult
import com.reflect.app.domain.repository.UserRepository

class LoginWithEmailUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(email: String, password: String): AuthResult {
        if (email.isBlank() || password.isBlank()) {
            return AuthResult.Error.InvalidCredentials
        }

        return userRepository.loginWithEmail(email, password)
    }
}