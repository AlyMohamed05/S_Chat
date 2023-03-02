package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.database.dao.ChannelDao
import com.example.core.database.dao.ChannelFullDao
import com.example.core.database.dao.MessagesDao
import com.example.core.database.dao.UserInfoDao
import com.example.core.database.entity.ChannelEntity
import com.example.core.database.entity.MessageEntity
import com.example.core.database.entity.UserInfoEntity

@Database(
    entities = [
        UserInfoEntity::class,
        ChannelEntity::class,
        MessageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SChatDatabase : RoomDatabase() {

    abstract fun usersInfoDao(): UserInfoDao

    abstract fun channelDao(): ChannelDao

    abstract fun messagesDao(): MessagesDao

    abstract fun channelFullDao(): ChannelFullDao

}