package com.silverbullet.core.network.connections

import com.silverbullet.core.network.BuildConfig
import com.silverbullet.core.network.connections.model.request.ConnectionRequest
import com.silverbullet.core.network.connections.model.response.ConnectionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitConnectionsApi {

    @POST("connections/create")
    suspend fun connectWithUser(
        @Body request: ConnectionRequest
    ): Response<ConnectionResponse>

    companion object {

        const val baseUrl = BuildConfig.BackendURL
    }
}