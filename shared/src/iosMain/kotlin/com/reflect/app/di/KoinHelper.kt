package com.reflect.app.di

import com.reflect.app.presentation.auth.AuthViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(commonModule, iosModule)
    }
}

object KoinKt {
    fun getKoin() = KoinHelper()
}

class KoinHelper : KoinComponent {
    fun getAuthViewModel() = get<AuthViewModel>()
}