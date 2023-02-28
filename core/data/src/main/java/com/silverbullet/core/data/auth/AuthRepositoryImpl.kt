package com.silverbullet.core.data.auth

import com.silverbullet.core.data.auth.results.RepoLoginResult
import com.silverbullet.core.data.auth.results.RepoSignupResult
import com.silverbullet.core.data.mapper.toUserInfo
import com.silverbullet.core.data.utils.RepoResult
import com.silverbullet.core.network.TokenController
import com.silverbullet.core.network.utils.SignupResult
import com.silverbullet.core.network.auth.AuthNetworkSource
import com.silverbullet.core.network.utils.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authNetworkSource: AuthNetworkSource,
    private val tokenController: TokenController
) : AuthRepository {

    override fun signup(
        username: String,
        name: String,
        password: String
    ): Flow<RepoResult<RepoSignupResult>> = flow {
        emit(RepoResult.Loading())
        val signupResult = authNetworkSource.signup(
            name = name,
            username = username,
            password = password
        )
        when (signupResult) {
            is SignupResult.Successful -> {
                val userInfo = signupResult.response.toUserInfo()
                val result = RepoSignupResult.SignedUpSuccessfully(userInfo)
                emit(RepoResult.HasResult(result))
            }
            is SignupResult.UsernameAlreadyUsed -> {
                emit(RepoResult.HasResult(result = RepoSignupResult.UsernameAlreadyUsed))
            }
            else -> Unit
        }
    }

    override fun login(username: String, password: String): Flow<RepoResult<RepoLoginResult>> =
        flow {
            emit(RepoResult.Loading())
            val loginResult = authNetworkSource.login(
                username = username,
                password = password
            )
            when (loginResult) {
                is LoginResult.LoggedIn -> {
                    tokenController.saveAccessToken(loginResult.accessToken)
                    tokenController.saveRefreshToken(loginResult.refreshToken)
                    emit(RepoResult.HasResult(result = RepoLoginResult.LoggedIn))
                }
                LoginResult.InvalidCredentials -> {
                    emit(RepoResult.HasResult(result = RepoLoginResult.InvalidCredentials))
                }

                LoginResult.UserNotFound -> {
                    emit(RepoResult.HasResult(result = RepoLoginResult.UserNotFound))
                }

                else -> Unit
            }
        }
}