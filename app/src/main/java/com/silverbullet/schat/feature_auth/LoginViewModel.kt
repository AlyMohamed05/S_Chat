package com.silverbullet.schat.feature_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()

    private val _loginButtonEnabled = MutableStateFlow(false)
    val loginButtonEnabled = _loginButtonEnabled.asStateFlow()

    init {
        viewModelScope.launch {
            delay(10000L)
            _loginScreenState.value = _loginScreenState.value.copy(isLocked = false)
        }
    }

    fun setUsernameValue(value: String) {
        _loginScreenState.value = _loginScreenState.value.copy(usernameText = value)
        invalidateLoginButtonState()
    }

    fun setPasswordValue(value: String) {
        _loginScreenState.value = _loginScreenState.value.copy(passwordText = value)
        invalidateLoginButtonState()
    }

    fun togglePasswordVisibility() {
        val current = _loginScreenState.value.shouldHidePassword
        _loginScreenState.value = _loginScreenState.value.copy(shouldHidePassword = !current)
    }

    fun login() {

    }

    private fun invalidateLoginButtonState() {
        val currentFieldsState = _loginScreenState.value
        _loginButtonEnabled.value = currentFieldsState.usernameText.isNotBlank() &&
                currentFieldsState.passwordText.isNotBlank()
    }
}