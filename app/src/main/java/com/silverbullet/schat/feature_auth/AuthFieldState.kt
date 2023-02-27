package com.silverbullet.schat.feature_auth

data class AuthFieldState(
    val value: String = "",
    val error: String? = null,
    val hasError: Boolean = false
)
