package com.silverbullet.core.network.auth

import com.silverbullet.core.network.BuildConfig
import com.silverbullet.core.network.auth.model.request.LoginRequest
import com.silverbullet.core.network.auth.model.request.RefreshTokenRequest
import com.silverbullet.core.network.auth.model.request.SignupRequest
import com.silverbullet.core.network.auth.model.response.RefreshTokenResponse
import com.silverbullet.core.network.auth.model.response.SignupResponse
import com.silverbullet.core.network.auth.model.response.TokenData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 *  [!!!] Notice that logout function is not here and extracted to another retrofit client because it needs
 *        to be an authenticated request and an interceptor must be added.
 */
internal interface RetrofitAuthApi {

    @POST("auth/signup")
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<TokenData>

    @GET("auth/is-valid/{username}")
    suspend fun isUsernameAvailable(@Path("username") username: String): Response<Unit>

    @POST
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>

    companion object{
        const val baseUrl = BuildConfig.BackendURL
    }

}