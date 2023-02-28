package com.silverbullet.core.network.channels

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.silverbullet.core.network.channels.model.NetworkChannelInfo
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.IOException

class ChannelsNetworkSourceImpl(
    private val networkJson: Json,
    private val authInterceptor: Interceptor
) : ChannelsNetworkSource {

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
            .baseUrl(RetrofitChannelsApi.baseUrl)
            .client(client)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RetrofitChannelsApi::class.java)
    }

    override suspend fun getUserChannels(): List<NetworkChannelInfo>? {
        return try {
            api
                .getChannels()
                .body()
        } catch (e: IOException) {
            null
        }
    }
}