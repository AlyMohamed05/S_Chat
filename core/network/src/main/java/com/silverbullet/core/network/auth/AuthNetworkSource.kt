package com.silverbullet.core.network.auth

import com.silverbullet.core.network.utils.LoginResult
import com.silverbullet.core.network.utils.SignupResult

/**
 * Used to send authentication requests over network
 */
interface AuthNetworkSource {

    suspend fun signup(
        name: String,
        username: String,
        password: String
    ): SignupResult

    suspend fun login(
        username: String,
        password: String
    ): LoginResult

    /**
     * To ensure that username is available before even sending signup request.
     * This is used when user is filling the signup form and you want to ensure that
     * username is available before sending signup request.
     */
    suspend fun isUsernameAvailable(username: String): Boolean?

    /**
     * @return token if refreshed else then it wasn't able to refresh token
     * // TODO: Implement better handling for refreshment failure
     */
    suspend fun refreshToken(refreshToken: String): String?

}