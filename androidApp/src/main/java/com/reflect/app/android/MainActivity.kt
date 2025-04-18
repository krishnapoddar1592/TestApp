package com.reflect.app.android

// File: androidApp/src/main/java/com/reflect.app/android/MainActivity.kt (updated)


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.reflect.app.android.ui.auth.AuthScreen
import com.reflect.app.android.ui.theme.ReflectAppTheme
import com.reflect.app.di.androidModule
import com.reflect.app.presentation.auth.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinApplication
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity(), KoinComponent {

    private val authViewModel by lazy {
        getKoin().get<AuthViewModel>()
    }

    private lateinit var googleSignInHelper: GoogleSignInHelper

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        googleSignInHelper.processResult(
            result.data,
            onSuccess = { idToken ->
                authViewModel.loginWithGoogle(idToken)
            },
            onError = { exception ->
                // Handle sign-in error
                println("Google Sign-In Error: ${exception.message}")
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        googleSignInHelper = GoogleSignInHelper(this)

        setContent {
            KoinApplication(application = {
                modules(com.reflect.app.di.commonModule, androidModule)
            }) {
                ReflectAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AuthScreen(
                            onGoogleSignInClick = { startGoogleSignIn() }
                        )
                    }
                }
            }
        }
    }

    private fun startGoogleSignIn() {
        val signInIntent = googleSignInHelper.getSignInIntent()
        googleSignInLauncher.launch(signInIntent)
    }
}

