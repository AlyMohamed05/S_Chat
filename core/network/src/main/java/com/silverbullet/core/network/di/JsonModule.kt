package com.silverbullet.core.network.di

import com.silverbullet.core.network.events.EventsJsonSerializer
import com.silverbullet.core.network.events.eventsSerializationModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JsonModule {

    @Provides
    @Singleton
    fun provideJsonSerializer(): Json = Json

    @Provides
    @Singleton
    @EventsJsonSerializer
    fun provideEventsJsonSerializer(): Json = Json {
        classDiscriminator = "event"
        serializersModule = eventsSerializationModule
    }
}