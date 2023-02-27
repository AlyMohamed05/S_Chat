package com.silverbullet.core.network.di

import com.silverbullet.core.network.auth.AuthNetworkSource
import com.silverbullet.core.network.auth.AuthNetworkSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJsonSerializer(): Json = Json

    @Provides
    @Singleton
    fun provideAuthNetworkSource(json: Json): AuthNetworkSource = AuthNetworkSourceImpl(json)
}