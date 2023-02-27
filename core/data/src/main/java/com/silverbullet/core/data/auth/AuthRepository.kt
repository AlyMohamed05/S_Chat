package com.silverbullet.core.data.auth

import com.silverbullet.core.data.auth.results.RepoLoginResult
import com.silverbullet.core.data.utils.RepoResult
import com.silverbullet.core.data.auth.results.RepoSignupResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signup(
        username: String,
        name: String,
        password: String
    ): Flow<RepoResult<RepoSignupResult>>

    fun login(
        username: String,
        password: String
    ): Flow<RepoResult<RepoLoginResult>>
}