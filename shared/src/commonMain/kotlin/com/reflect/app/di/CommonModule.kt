package com.reflect.app.di

// File: shared/src/commonMain/kotlin/com/reflect.app/di/CommonModule.kt


import com.reflect.app.domain.usecase.auth.AppleSignInUseCase
import com.reflect.app.domain.usecase.auth.GoogleSignInUseCase
import com.reflect.app.domain.usecase.auth.LoginWithEmailUseCase
import com.reflect.app.domain.usecase.auth.RegisterUseCase
import com.reflect.app.presentation.auth.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val commonModule = module {
    // Coroutine scope for ViewModels
    factory { CoroutineScope(SupervisorJob() + Dispatchers.Main) }

    // Use cases
    factory { LoginWithEmailUseCase(get()) }
    factory { GoogleSignInUseCase(get()) }
    factory { AppleSignInUseCase(get()) }
    factory { RegisterUseCase(get()) }

    // ViewModels
    factory { AuthViewModel(get(), get(), get(), get(), get()) }

}
