package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.example.core.database.entity.ChannelEntity
import com.example.core.database.entity.MessageEntity
import com.example.core.database.entity.UserInfoEntity
import com.example.core.database.model.ChannelFull

@Dao
interface ChannelFullDao {

    @Insert
    suspend fun insertChannel(channelEntity: ChannelEntity)

    @Insert
    suspend fun insertUserInfo(userInfo: UserInfoEntity)

    @Insert
    suspend fun insertMessage(messageEntity: MessageEntity)

    @Transaction
    suspend fun insertChannelFull(channel: ChannelFull) {
        insertUserInfo(channel.friend)
        channel.lastSeenMessage?.let { message -> insertMessage(message) }
        insertChannel(channel.channel)
    }
}