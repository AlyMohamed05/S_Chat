package com.silverbullet.core.network.di

import com.silverbullet.core.network.events.SChatNetworkEvents
import com.silverbullet.core.network.events.SChatNetworkEventsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class EventsModule {

    @Binds
    abstract fun bindSChatNetworkEvents(sChatNetworkEvents: SChatNetworkEventsImpl): SChatNetworkEvents
}