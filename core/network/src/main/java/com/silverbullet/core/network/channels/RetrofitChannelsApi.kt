package com.silverbullet.core.network.channels

import com.silverbullet.core.network.BuildConfig
import com.silverbullet.core.network.channels.model.NetworkChannelInfo
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitChannelsApi {

    @GET("channels")
    suspend fun getChannels(): Response<List<NetworkChannelInfo>>

    companion object{
        const val baseUrl = BuildConfig.BackendURL
    }
}