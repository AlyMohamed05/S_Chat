package com.silverbullet.schat.feature_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverbullet.core.data.auth.AuthRepository
import com.silverbullet.core.data.auth.results.RepoLoginResult
import com.silverbullet.core.data.utils.RepoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _usernameFieldState = MutableStateFlow(AuthFieldState())
    val usernameFieldState = _usernameFieldState.asStateFlow()

    private val _passwordFieldState = MutableStateFlow(AuthFieldState())
    val passwordFieldState = _passwordFieldState.asStateFlow()

    private val _hidePassword = MutableStateFlow(true)
    val hidePassword = _hidePassword.asStateFlow()

    private val _loginButtonEnabled = MutableStateFlow(false)
    val loginButtonEnabled = _loginButtonEnabled.asStateFlow()

    // To control if the lock icon drawn is locked or not
    private val _isLocked = MutableStateFlow(true)
    val isLocked = _isLocked.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()

    fun setUsernameValue(value: String) {
        _usernameFieldState.value = AuthFieldState(value = value)
        invalidateLoginButtonState()
    }

    fun setPasswordValue(value: String) {
        _passwordFieldState.value = AuthFieldState(value = value)
        invalidateLoginButtonState()
    }

    fun togglePasswordVisibility() {
        _hidePassword.value = !_hidePassword.value
    }

    fun login() {
        if (!validateAllFields())
            return
        viewModelScope.launch {
            authRepository
                .login(
                    username = _usernameFieldState.value.value,
                    password = _passwordFieldState.value.value
                )
                .collect { loginRepoResult ->
                    when (loginRepoResult) {
                        is RepoResult.HasResult -> {
                            when (loginRepoResult.result) {

                                RepoLoginResult.LoggedIn -> {
                                    _event.emit(UiEvent.IsLoggedIn)
                                }

                                RepoLoginResult.UserNotFound -> {
                                    _event.emit(UiEvent.ToastMessage("user not found"))
                                }

                                RepoLoginResult.InvalidCredentials -> {
                                    _event.emit(UiEvent.ToastMessage("Invalid credentials"))
                                }
                            }
                            _isLoading.value = false
                        }

                        is RepoResult.Loading -> {
                            _isLoading.value = true
                        }
                    }
                }
        }
    }

    private fun invalidateLoginButtonState() {
        _loginButtonEnabled.value = _usernameFieldState.value.value.isNotBlank() &&
                _passwordFieldState.value.value.isNotBlank()
    }

    private fun validateAllFields(): Boolean {
        var valid = true
        if (_usernameFieldState.value.value.isBlank()) {
            _usernameFieldState.value = _usernameFieldState.value.copy(
                hasError = true,
                error = "this field can't be empty"
            )
            valid = false
        }
        if (_passwordFieldState.value.value.length < 8) {
            _passwordFieldState.value = _passwordFieldState.value.copy(
                hasError = true,
                error = "Password can't be less than 8 characters"
            )
            valid = false
        }
        return valid
    }

    sealed interface UiEvent {

        object IsLoggedIn : UiEvent

        data class ToastMessage(val message: String) : UiEvent
    }
}