package com.silverbullet.schat.feature_auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : ViewModel() {

    private val _signupScreenState = MutableStateFlow(SignupScreenState())
    val signupScreenState = _signupScreenState.asStateFlow()

    fun onEvent(event: SignupScreenEvent) {
        when (event) {
            is SignupScreenEvent.NameFieldChanged -> _signupScreenState.value =
                _signupScreenState.value.copy(nameText = event.name)

            is SignupScreenEvent.UsernameFieldChanged -> _signupScreenState.value =
                _signupScreenState.value.copy(usernameText = event.username)

            is SignupScreenEvent.PasswordFieldChanged -> _signupScreenState.value =
                _signupScreenState.value.copy(passwordText = event.password)

            SignupScreenEvent.TogglePasswordVisibility -> {
                val currentVisibility = _signupScreenState.value.shouldHidePassword
                _signupScreenState.value =
                    _signupScreenState.value.copy(shouldHidePassword = !currentVisibility)
            }

            SignupScreenEvent.Signup -> signup()
        }
    }

    private fun signup(){
        // TODO: Implement signup
    }
}