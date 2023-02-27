package com.silverbullet.core.data.auth.results

import com.silverbullet.core.model.UserInfo

sealed interface RepoSignupResult {

    class SignedUpSuccessfully(
        val userInfo: UserInfo
    ): RepoSignupResult

    object UsernameAlreadyUsed: RepoSignupResult
}