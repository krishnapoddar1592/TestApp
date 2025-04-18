// File: shared/src/androidMain/kotlin/com/reflect/app/di/AndroidModule.kt
package com.reflect.app.di

import android.content.Context
import com.reflect.app.data.local.dao.UserDao
import com.reflect.app.data.local.dao.UserDaoImpl
import com.reflect.app.data.remote.api.FirebaseAuthFactory
import com.reflect.app.data.remote.api.FirebaseAuthInterface
import com.reflect.app.data.repository.UserRepositoryImpl
import com.reflect.app.domain.repository.UserRepository
import com.reflect.app.domain.usecase.auth.AppleSignInUseCase
import com.reflect.app.domain.usecase.auth.GoogleSignInUseCase
import com.reflect.app.domain.usecase.auth.LoginWithEmailUseCase
import com.reflect.app.domain.usecase.auth.RegisterUseCase
import com.reflect.app.presentation.auth.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val androidModule = module {
    // Context
    single { get<android.app.Application>() as Context }

    // CoroutineScope for ViewModels
    factory { CoroutineScope(SupervisorJob() + Dispatchers.Main) }

    // Data sources
    factory<UserDao> { UserDaoImpl(get()) }
    factory<FirebaseAuthInterface> { FirebaseAuthFactory.createFirebaseAuth() }

    // Repository
    factory<UserRepository> { UserRepositoryImpl(get()) }

    // Use cases
    factory { LoginWithEmailUseCase(get()) }
    factory { GoogleSignInUseCase(get()) }
    factory { AppleSignInUseCase(get()) }
    factory { RegisterUseCase(get()) }

    // ViewModel with explicit parameters
    factory {
        AuthViewModel(
            loginWithEmailUseCase = get(),
            googleSignInUseCase = get(),
            appleSignInUseCase = get(),
            registerUseCase = get(),
            coroutineScope = get()
        )
    }
}