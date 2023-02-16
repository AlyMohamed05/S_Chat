package com.silverbullet.schat.feature_auth

sealed interface SignupScreenEvent{

    data class NameFieldChanged(val name: String): SignupScreenEvent

    data class UsernameFieldChanged(val username: String): SignupScreenEvent

    data class PasswordFieldChanged(val password: String): SignupScreenEvent

    object TogglePasswordVisibility: SignupScreenEvent

    object Signup: SignupScreenEvent
}
