package com.example.investment

import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.investment.navigation.BottomBar
import com.example.investment.navigation.NavHostComposable
import com.example.investment.ui.theme.InvestmentTheme
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<MainActivityViewModel>()
            val isAuthenticated by viewModel.isAuthenticated.collectAsState()

            if (isAuthenticated) {
                AppContent()
            } else {
                Authentication(viewModel)
            }
        }
    }


    @Composable
    fun AppContent() {
        val navController = rememberNavController()
        InvestmentTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    bottomBar = {
                        BottomBar { navController.navigate(it) }
                    },
                ) { innerPadding ->
                    NavHostComposable(innerPadding, navController)
                }
            }
        }
    }

    @Composable
    fun Authentication(viewModel: MainActivityViewModel) {
        val context = LocalContext.current as FragmentActivity
        val isAuthenticated by viewModel.isAuthenticated.collectAsState()
        val biometricManager = remember { BiometricManager.from(context) }
        val isBiometricAvailable = remember {
            biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        }
        when (isBiometricAvailable) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // Las características biométricas están disponibles
                if (!isAuthenticated) {
                    // Si no está autenticado, iniciar la autenticación biométrica
                    viewModel.checkAuthentication(context)
                }
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                // No biometric features available on this device
                Text(text = "This phone is not prepared for biometric features")
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                // Biometric features are currently unavailable.
                Text(text = "Biometric auth is unavailable")
            }

            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                // Biometric features available but a security vulnerability has been discovered
                Text(text = "You can't use biometric auth until you have updated your security details")
            }

            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                // Biometric features are currently unavailable because the specified options are incompatible with the current Android version..
                Text(text = "You can't use biometric auth with this Android version")
            }

            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                // Unable to determine whether the user can authenticate using biometrics
                Text(text = "You can't use biometric auth")
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // The user can't authenticate because no biometric or device credential is enrolled.
                Text(text = "You can't use biometric auth")
            }
        }
    }

}
