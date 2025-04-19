package com.reflect.app.di

import com.reflect.app.presentation.auth.AuthViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import kotlin.experimental.ExperimentalObjCName

fun initKoin() {
    startKoin {
        modules(commonModule, iosModule)
    }
}

object KoinKt {
    fun getKoin() = KoinHelper()
}
@OptIn(ExperimentalObjCName::class)
@ObjCName("KoinHelper","KoinHelper")
class KoinHelper : KoinComponent {
    fun getAuthViewModel() = get<AuthViewModel>()
}