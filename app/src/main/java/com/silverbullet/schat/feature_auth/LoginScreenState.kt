package com.silverbullet.schat.feature_auth

/**
 * @param isLocked indicate Either the user is logged in or not by locking the
 * lock icon on the screen or unlocking it.
 */
data class LoginScreenState(
    val usernameText: String = "",
    val passwordText: String = "",
    val shouldHidePassword: Boolean = true,
    val isLoading: Boolean = false,
    val isLocked: Boolean =  true
)
