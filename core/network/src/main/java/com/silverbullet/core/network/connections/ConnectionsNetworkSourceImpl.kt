package com.silverbullet.core.network.connections

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.silverbullet.core.network.connections.model.request.ConnectionRequest
import com.silverbullet.core.network.utils.ApiErrorCodes
import com.silverbullet.core.network.utils.ConnectionResult
import com.silverbullet.core.network.utils.toApiErrorResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.IOException

class ConnectionsNetworkSourceImpl(
    private val networkJson: Json,
    private val authInterceptor: Interceptor
) : ConnectionsNetworkSource {

    private val client by lazy {
        OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
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
            .baseUrl(RetrofitConnectionsApi.baseUrl)
            .client(client)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RetrofitConnectionsApi::class.java)
    }

    override suspend fun connectWithUser(username: String): ConnectionResult {
        val connectionRequest = ConnectionRequest(username)
        try {
            val response = api.connectWithUser(connectionRequest)
            if (response.code() == 200 && response.body() != null && response.body()!!.status) {
                // Then connection is successful
                return ConnectionResult.ConnectedSuccessfully
            }
            val errorResponse =
                response.toApiErrorResponse(networkJson) ?: return ConnectionResult.UnexpectedError
            return when (errorResponse.errorCode) {
                ApiErrorCodes.UserNotFoundCode -> ConnectionResult.UserNotFound
                ApiErrorCodes.AlreadyConnectedUsers -> ConnectionResult.AlreadyConnected
                else -> ConnectionResult.UnexpectedError
            }
        } catch (e: IOException) {
            return ConnectionResult.NetworkError
        }
    }
}