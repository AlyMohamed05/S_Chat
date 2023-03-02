package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.core.database.entity.ChannelEntity
import com.example.core.database.model.ChannelFull
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChannel(channel: ChannelEntity)

    @Query("UPDATE t_channels SET last_message_id  = :lastMessageId WHERE id = :channelId")
    suspend fun updateLastMessageForChannel(channelId: Int, lastMessageId: String)

    @Query("SELECT * FROM t_channels WHERE id = :id")
    @Transaction
    suspend fun getChannelById(id: Int): ChannelFull?

    @Query("SELECT * FROM t_channels")
    @Transaction
    fun getChannels(): Flow<List<ChannelFull>>
}