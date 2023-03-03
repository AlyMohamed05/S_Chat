package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.example.core.database.entity.ChannelEntity
import com.example.core.database.entity.MessageEntity
import com.example.core.database.entity.UserInfoEntity
import com.example.core.database.model.ChannelFull

@Dao
interface ChannelFullDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllChannel(channels: List<ChannelEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllUsersInfos(usersInfos: List<UserInfoEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMessages(messages: List<MessageEntity>)

    @Transaction
    suspend fun insertAllChannelFull(channels: List<ChannelFull>) {
        val usersInfos = channels.map { it.friend }
        val channelsInfos = channels.map { it.channel }
        val lastSeenMessages = channels.mapNotNull { it.lastSeenMessage }
        insertAllUsersInfos(usersInfos)
        insertAllChannel(channelsInfos)
        insertAllMessages(lastSeenMessages)
    }
}