package com.silverbullet.schat.feature_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverbullet.core.data.auth.AuthRepository
import com.silverbullet.core.data.auth.results.RepoSignupResult
import com.silverbullet.core.data.utils.RepoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _usernameFieldState = MutableStateFlow(AuthFieldState())
    val usernameState = _usernameFieldState.asStateFlow()

    private val _nameFieldState = MutableStateFlow(AuthFieldState())
    val nameFieldState = _nameFieldState.asStateFlow()

    private val _passwordFieldState = MutableStateFlow(AuthFieldState())
    val passwordFieldState = _passwordFieldState.asStateFlow()

    private val _hidePassword = MutableStateFlow(true)
    val hidePassword = _hidePassword.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    fun onEvent(event: SignupScreenEvent) {
        when (event) {
            is SignupScreenEvent.NameFieldChanged -> _nameFieldState.value =
                AuthFieldState(value = event.name)

            // Use a new state to remove other errors on the field in case they exist
            is SignupScreenEvent.UsernameFieldChanged -> _usernameFieldState.value =
                AuthFieldState(value = event.username)

            is SignupScreenEvent.PasswordFieldChanged -> _passwordFieldState.value =
                AuthFieldState(value = event.password)

            SignupScreenEvent.TogglePasswordVisibility -> _hidePassword.value = !_hidePassword.value

            SignupScreenEvent.Signup -> signup()
        }
    }

    private fun signup() {
        if (!validateAllFields())
            return
        viewModelScope.launch {
            authRepository
                .signup(
                    username = _usernameFieldState.value.value,
                    name = _nameFieldState.value.value,
                    password = _passwordFieldState.value.value
                )
                .collect { repoResult ->
                    when (repoResult) {
                        is RepoResult.HasResult -> {
                            when (repoResult.result) {
                                is RepoSignupResult.SignedUpSuccessfully -> onSignup()

                                RepoSignupResult.UsernameAlreadyUsed -> _usernameFieldState.value =
                                    _usernameFieldState.value.copy(
                                        hasError = true,
                                        error = "username already exists"
                                    )
                            }
                        }
                        is RepoResult.Loading -> {
                            _isLoading.value = true
                        }
                    }
                }
        }
    }

    private fun onSignup() {
        // proceed navigation to login screen and pass the username and password
        viewModelScope.launch {
            _events.emit(UiEvent.OnSignupClass)
        }
    }

    /**
     * Returns if all fields are valid or no,
     * it also sets the error messages if needed
     */
    private fun validateAllFields(): Boolean {
        var valid = true
        if (_usernameFieldState.value.value.isBlank()) {
            _usernameFieldState.value = _usernameFieldState.value.copy(
                hasError = true,
                error = "this field can't be empty"
            )
            valid = false
        }
        if (_nameFieldState.value.value.isBlank()) {
            _nameFieldState.value = _nameFieldState.value.copy(
                hasError = true,
                error = "this field can't be empty"
            )
            valid = false
        }
        if (_passwordFieldState.value.value.length < 8) {
            _passwordFieldState.value = _passwordFieldState.value.copy(
                hasError = true,
                error = "Password must be at least 8 characters"
            )
            valid = false
        }
        return valid
    }

    sealed interface UiEvent {

        object OnSignupClass : UiEvent
    }
}