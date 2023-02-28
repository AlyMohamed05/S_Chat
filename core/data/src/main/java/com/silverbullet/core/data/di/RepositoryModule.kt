package com.silverbullet.core.data.di

import com.silverbullet.core.data.TokenControllerImpl
import com.silverbullet.core.data.auth.AuthRepository
import com.silverbullet.core.data.auth.AuthRepositoryImpl
import com.silverbullet.core.network.TokenController
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindTokenController(tokenControllerImpl: TokenControllerImpl): TokenController
}