package com.silverbullet.core.network.utils

import com.silverbullet.core.network.auth.model.response.SignupResponse

sealed interface SignupResult {

    data class Successful(val response: SignupResponse) : SignupResult

    object UsernameAlreadyUsed : SignupResult

    object UnexpectedError : SignupResult

    object NetworkError : SignupResult
}

sealed interface LoginResult {

    data class LoggedIn(
        val accessToken: String,
        val refreshToken: String
    ) : LoginResult

    object InvalidCredentials : LoginResult

    object UserNotFound : LoginResult

    object UnexpectedError : LoginResult

    object NetworkError : LoginResult
}