package com.silverbullet.core.network

/**
 * This Controller is responsible for retrieving the token from the local storage, and also
 * updating it in the local storage.
 * notice that the access token will be updated (refreshed) automatically by the network interceptor,
 * so in order to be able to update the token in the local storage without having to expose the local storage
 * to the network layer, the network layer will just define this interface in here, and anyway the repository already
 * depends on the network layer , given that also the repository has access to the local storage so the repository can just
 * implement this controller for the network layer.
 */
interface TokenController {

    fun hasAccessToken(): Boolean

    fun getAccessToken(): String?

    fun getRefreshToken(): String?

    fun saveAccessToken(token: String)

    fun saveRefreshToken(token: String)
}