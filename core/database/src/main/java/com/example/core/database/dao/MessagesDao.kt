package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMessages(messages: List<MessageEntity>)

    @Query("UPDATE t_messages SET seen =:seen WHERE id = :messageId")
    suspend fun updateMessageSeenStatus(messageId: String, seen: Boolean)

    @Query("SELECT * FROM t_messages WHERE channel_id = :channelId")
    fun getChannelMessages(channelId: Int): Flow<List<MessageEntity>>
}