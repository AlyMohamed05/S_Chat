package com.silverbullet.core.data.auth.results

sealed interface RepoLoginResult {

    object LoggedIn : RepoLoginResult

    object UserNotFound : RepoLoginResult

    object InvalidCredentials : RepoLoginResult
}