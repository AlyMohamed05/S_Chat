package com.silverbullet.core.network.messages

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.silverbullet.core.network.messages.model.request.MarkAsSeenRequest
import com.silverbullet.core.network.messages.model.request.SendMessageRequest
import com.silverbullet.core.network.utils.ApiErrorCodes
import com.silverbullet.core.network.utils.ChannelMessagesResult
import com.silverbullet.core.network.utils.MarkAsSeenResult
import com.silverbullet.core.network.utils.SendMessageResult
import com.silverbullet.core.network.utils.toApiErrorResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.IOException

class MessagesNetworkSourceImpl(
    private val networkJson: Json,
    private val authInterceptor: Interceptor
) : MessagesNetworkSource {

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
            .baseUrl(RetrofitMessagesApi.baseUrl)
            .client(client)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RetrofitMessagesApi::class.java)
    }

    override suspend fun getChannelMessages(channelId: Int): ChannelMessagesResult {
        return try {
            val networkResult = api.fetchChannelMessages(channelId)
            if (networkResult.isSuccessful && networkResult.body() != null)
                ChannelMessagesResult.Successful(networkResult.body()!!)
            else
                ChannelMessagesResult.UnexpectedError
        } catch (e: IOException) {
            ChannelMessagesResult.NetworkError
        }
    }

    override suspend fun sendMessage(message: String, username: String): SendMessageResult {
        return try {
            val request = SendMessageRequest(receiverUsername = username, text = message)
            val networkResult = api.sendMessage(request)
            if (networkResult.isSuccessful && networkResult.body() != null) {
                SendMessageResult.Sent
            } else {
                val errorMessage = networkResult.toApiErrorResponse(networkJson)
                    ?: return SendMessageResult.UnexpectedError
                when (errorMessage.errorCode) {
                    ApiErrorCodes.UserNotFoundCode -> SendMessageResult.UserNotFound
                    ApiErrorCodes.NoCommonChannelBetweenUsersCode -> SendMessageResult.NotConnectedToUser
                    else -> SendMessageResult.UnexpectedError
                }
            }
        } catch (e: IOException) {
            SendMessageResult.NetworkError
        }
    }

    override suspend fun markAsSeen(messageId: String): MarkAsSeenResult {
        return try {
            val request = MarkAsSeenRequest(messageId)
            val networkResult = api.markMessageAsSeen(request)
            if (networkResult.isSuccessful && networkResult.body() != null) {
                MarkAsSeenResult.Successful
            } else {
                val errorMessage = networkResult.toApiErrorResponse(networkJson)
                    ?: return MarkAsSeenResult.UnexpectedError
                when (errorMessage.errorCode) {
                    ApiErrorCodes.MESSAGE_NOT_FOUND -> MarkAsSeenResult.MessageNotFound
                    else -> MarkAsSeenResult.UnexpectedError
                }
            }
        } catch (e: IOException) {
            MarkAsSeenResult.NetworkError
        }
    }
}