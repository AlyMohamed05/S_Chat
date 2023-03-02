package com.silverbullet.core.network.messages

import com.silverbullet.core.network.BuildConfig
import com.silverbullet.core.network.messages.model.request.MarkAsSeenRequest
import com.silverbullet.core.network.messages.model.request.SendMessageRequest
import com.silverbullet.core.network.model.response.NetworkMessage
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitMessagesApi {

    @GET("messages/{channelId}")
    suspend fun fetchChannelMessages(
        @Path("channelId") channelId: Int
    ): Response<List<NetworkMessage>>

    @POST("messages/mark-as-seen")
    suspend fun markMessageAsSeen(
        @Body request: MarkAsSeenRequest
    ): Response<Map<String, Boolean>>

    @POST("messages/send")
    suspend fun sendMessage(
        @Body request: SendMessageRequest
    ): Response<Map<String, Boolean>>

    companion object {

        const val baseUrl = BuildConfig.BackendURL
    }
}