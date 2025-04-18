package com.reflect.app.domain.usecase.auth

// File: shared/src/commonMain/kotlin/com/reflect.app/domain/usecase/auth/RegisterUseCase.kt


import com.reflect.app.domain.model.AuthResult
import com.reflect.app.domain.repository.UserRepository

class RegisterUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(email: String, password: String, displayName: String?): AuthResult {
        if (email.isBlank() || password.isBlank()) {
            return AuthResult.Error.InvalidCredentials
        }

        if (password.length < 6) {
            return AuthResult.Error.Unknown("Password must be at least 6 characters")
        }

        return userRepository.register(email, password, displayName)
    }
}