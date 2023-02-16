package com.silverbullet.schat.feature_auth

data class SignupScreenState(
    val usernameText: String = "",
    val nameText: String = "",
    val passwordText: String = "",
    val shouldHidePassword: Boolean = true,
)
