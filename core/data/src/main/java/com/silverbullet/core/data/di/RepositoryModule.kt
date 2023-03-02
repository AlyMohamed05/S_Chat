package com.silverbullet.core.data.di

import com.silverbullet.core.data.TokenControllerImpl
import com.silverbullet.core.data.auth.AuthRepository
import com.silverbullet.core.data.auth.AuthRepositoryImpl
import com.silverbullet.core.data.channels.ChannelsRepository
import com.silverbullet.core.data.channels.ChannelsRepositoryImpl
import com.silverbullet.core.data.connections.ConnectionsRepository
import com.silverbullet.core.data.connections.ConnectionsRepositoryImpl
import com.silverbullet.core.data.messages.MessagesRepository
import com.silverbullet.core.data.messages.MessagesRepositoryImpl
import com.silverbullet.core.data.preferences.AndroidPreferences
import com.silverbullet.core.data.preferences.Preferences
import com.silverbullet.core.network.TokenController
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPreferences(preferences: AndroidPreferences): Preferences

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindConnectionsRepository(connectionsRepositoryImpl: ConnectionsRepositoryImpl): ConnectionsRepository

    @Binds
    abstract fun bindChannelsRepository(channelsRepositoryImpl: ChannelsRepositoryImpl): ChannelsRepository

    @Binds
    abstract fun bindTokenController(tokenControllerImpl: TokenControllerImpl): TokenController

    @Binds
    abstract fun bindMessagesRepository(messagesRepository: MessagesRepositoryImpl): MessagesRepository
}