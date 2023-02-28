package com.silverbullet.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.silverbullet.core.network.auth.model.request.RefreshTokenRequest
import com.silverbullet.core.network.auth.model.response.RefreshTokenResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SChatApiNetworkInterceptor @Inject constructor(
    private val networkJson: Json,
    private val tokenController: TokenController
) : Interceptor {

    private var accessToken = tokenController.getAccessToken()
    private var refreshToken = tokenController.getRefreshToken()

    // This variable need to be synchronized access all threads
    private var isRefreshing = false

    private val lock = Any()
    private var lastRefresh: Long? = null

    @OptIn(ExperimentalSerializationApi::class)
    private val refreshTokenApi by lazy {
        Retrofit
            .Builder()
            .baseUrl(RetrofitRefreshTokenApi.baseUrl)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RetrofitRefreshTokenApi::class.java)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        accessToken?.let { token ->
            val authenticatedRequest = originalRequest
                .newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            val response = chain.proceed(authenticatedRequest)
            if (response.code == 401 && refreshToken != null) {
                synchronized(lock) {
                    // Then access token has probably expired and we need to refresh it.
                    lastRefresh?.let { refreshTime ->
                        val timeSinceLastRefresh = System.currentTimeMillis() - refreshTime
                        if (timeSinceLastRefresh > TimeUnit.HOURS.toMillis(1)) {
                            // Then probably there is a problem with token because it has just refreshed and
                            // It's not valid.
                            // just return the response
                            return response
                        }
                    }
                    if (!isRefreshing) {
                        isRefreshing = true
                        val newAccessToken =
                            refreshTokenApi.refreshToken(RefreshTokenRequest(refreshToken!!))
                                .execute()
                                .body()
                                ?.newToken
                        if (newAccessToken != null) {
                            // save the new token and set the current access token
                            accessToken = newAccessToken
                            tokenController.saveAccessToken(newAccessToken)
                            isRefreshing = false
                            lastRefresh = System.currentTimeMillis()
                            val newRequest = originalRequest
                                .newBuilder()
                                .header("Authorization", "Bearer $newAccessToken")
                                .build()
                            return chain.proceed(newRequest)
                        }
                    }
                }
            }else{
                // There is an access token (which is invalid) but there is no refresh token
                // so we should just return the response
                return response
            }
        }
        // if access token is null, then it might be that user hasn't logged in yet
        // or the user has just logged in at the moment and we still didn't try to get the token
        // from the token controller again so we need to try to get it.
        tokenController
            .getAccessToken()
            ?.let { token ->
                // Then user has just logged in and we have the token
                val authenticatedRequest = originalRequest
                    .newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                return chain.proceed(authenticatedRequest)
            }
        return chain.proceed(originalRequest)
    }

    internal interface RetrofitRefreshTokenApi {

        @POST("auth/refresh")
        fun refreshToken(@Body request: RefreshTokenRequest): Call<RefreshTokenResponse>

        companion object {
            const val baseUrl = BuildConfig.BackendURL
        }
    }
}