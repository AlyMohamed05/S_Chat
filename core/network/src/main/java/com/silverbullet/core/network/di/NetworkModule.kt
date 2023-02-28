package com.silverbullet.core.network.di

import com.silverbullet.core.network.SChatApiNetworkInterceptor
import com.silverbullet.core.network.TokenController
import com.silverbullet.core.network.auth.AuthNetworkSource
import com.silverbullet.core.network.auth.AuthNetworkSourceImpl
import com.silverbullet.core.network.connections.ConnectionsNetworkSource
import com.silverbullet.core.network.connections.ConnectionsNetworkSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJsonSerializer(): Json = Json

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
    fun provideAuthNetworkSource(
        json: Json,
        interceptor: Interceptor
    ): AuthNetworkSource = AuthNetworkSourceImpl(json, interceptor)

    @Provides
    @Singleton
    fun provideConnectionsNetworkSource(
        json: Json,
        interceptor: Interceptor
    ): ConnectionsNetworkSource = ConnectionsNetworkSourceImpl(json, interceptor)
}