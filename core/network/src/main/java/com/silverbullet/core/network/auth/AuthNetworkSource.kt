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

}