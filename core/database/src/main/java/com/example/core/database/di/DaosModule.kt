package com.example.core.database.di

import com.example.core.database.SChatDatabase
import com.example.core.database.dao.ChannelDao
import com.example.core.database.dao.ChannelFullDao
import com.example.core.database.dao.MessagesDao
import com.example.core.database.dao.UserInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun provideUserInfoDao(db: SChatDatabase): UserInfoDao = db.usersInfoDao()

    @Provides
    fun provideChannelDao(db: SChatDatabase): ChannelDao = db.channelDao()

    @Provides
    fun provideMessagesDao(db: SChatDatabase): MessagesDao = db.messagesDao()

    @Provides
    fun provideChannelFullDao(db: SChatDatabase): ChannelFullDao = db.channelFullDao()
}