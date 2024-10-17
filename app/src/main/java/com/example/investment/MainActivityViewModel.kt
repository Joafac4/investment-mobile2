package com.example.investment

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.investment.security.BiometricAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val biometricAuthManager: BiometricAuthManager,
) : ViewModel() {
    private var _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    fun checkAuthentication(activity: FragmentActivity){
        biometricAuthManager.authenticate(
            activity,
            onError = {
                _isAuthenticated.value = false
                Toast.makeText(context, "There was an error in the authentication", Toast.LENGTH_SHORT).show()
            },
            onSuccess = {
                _isAuthenticated.value = true
            },
            onFail = {
                _isAuthenticated.value = false
                Toast.makeText(context, "The authentication failed, try again", Toast.LENGTH_SHORT).show()
            }
        )
    }
}