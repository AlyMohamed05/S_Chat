package com.silverbullet.core.network.auth

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.silverbullet.core.network.utils.LoginResult
import com.silverbullet.core.network.utils.SignupResult
import com.silverbullet.core.network.auth.model.request.LoginRequest
import com.silverbullet.core.network.auth.model.request.SignupRequest
import com.silverbullet.core.network.utils.ApiErrorCodes
import com.silverbullet.core.network.utils.toApiErrorResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.IOException

class AuthNetworkSourceImpl(
    private val networkJson: Json,
    private val apiAuthInterceptor: Interceptor
) : AuthNetworkSource {

    private val client by lazy {
        OkHttpClient
            .Builder()
            .addInterceptor(apiAuthInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
            )
            .build()
    }

    // Lazily initialize the api
    @OptIn(ExperimentalSerializationApi::class)
    private val api by lazy {
        Retrofit
            .Builder()
            .baseUrl(RetrofitAuthApi.baseUrl)
            .client(client)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RetrofitAuthApi::class.java)
    }

    override suspend fun signup(name: String, username: String, password: String): SignupResult {
        try {
            val signupRequest = SignupRequest(
                username = username,
                name = name,
                password = password
            )
            val response = api.signup(signupRequest)
            if (response.isSuccessful && response.body() != null) {
                return SignupResult.Successful(response.body()!!)
            }
            // Then request has failed
            val errorResponse =
                response.toApiErrorResponse(networkJson) ?: return SignupResult.UnexpectedError
            return when (errorResponse.errorCode) {
                ApiErrorCodes.UsernameAlreadyExistsCode -> SignupResult.UsernameAlreadyUsed
                else -> SignupResult.UnexpectedError
            }
        } catch (exception: IOException) {
            return SignupResult.NetworkError
        }
    }

    override suspend fun login(username: String, password: String): LoginResult {
        try {
            val loginRequest = LoginRequest(username = username, password = password)
            val response = api.login(loginRequest)
            if (response.isSuccessful && response.body() != null) {
                return LoginResult.LoggedIn(
                    accessToken = response.body()!!.accessToken,
                    refreshToken = response.body()!!.refreshToken
                )
            }
            // Then request has failed
            val errorResponse =
                response.toApiErrorResponse(networkJson) ?: return LoginResult.UnexpectedError
            return when (errorResponse.errorCode) {
                ApiErrorCodes.InvalidCredentialsCode -> LoginResult.InvalidCredentials
                ApiErrorCodes.UserNotFoundCode -> LoginResult.UserNotFound
                else -> LoginResult.UnexpectedError
            }
        } catch (e: IOException) {
            return LoginResult.NetworkError
        }
    }

    override suspend fun isUsernameAvailable(username: String): Boolean? {
        try {
            val response = api.isUsernameAvailable(username)
            if (response.isSuccessful)
                return true
            return false
        } catch (e: IOException) {
            return null
        }
    }
}