package com.reflect.app.di

import com.reflect.app.data.local.dao.UserDao
import com.reflect.app.data.local.dao.UserDaoImpl
import com.reflect.app.data.remote.api.IosFirebaseAuth
import com.reflect.app.data.remote.api.FirebaseAuthInterface
import com.reflect.app.data.repository.UserRepositoryImpl
import com.reflect.app.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val iosModule = module {
    // Coroutine scope for ViewModels
    factory { CoroutineScope(SupervisorJob() + Dispatchers.Main) }

    // Data sources
    factory<UserDao> { UserDaoImpl() }
    factory<FirebaseAuthInterface> { IosFirebaseAuth() }

    // Repositories
    factory<UserRepository> { UserRepositoryImpl(get()) }

    // Regular factory for AuthViewModel (not Android's ViewModel)
    factory {
        com.reflect.app.presentation.auth.AuthViewModel(
            loginWithEmailUseCase = get(),
            googleSignInUseCase = get(),
            appleSignInUseCase = get(),
            registerUseCase = get(),
            coroutineScope = get()
        )
    }
}