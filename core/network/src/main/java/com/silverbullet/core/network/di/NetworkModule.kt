package com.silverbullet.core.network.di

import com.silverbullet.core.network.SChatApiNetworkInterceptor
import com.silverbullet.core.network.TokenController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.WebSockets
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSChatApiNetworkInterceptor(
        tokenController: TokenController,
        networkJson: Json
    ): Interceptor {
        return SChatApiNetworkInterceptor(
            tokenController = tokenController,
            networkJson = networkJson
        )
    }

    @Provides
    @Singleton
    fun provideKtorHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(WebSockets){
                this.pingInterval = 20000L
            }
        }
    }
}