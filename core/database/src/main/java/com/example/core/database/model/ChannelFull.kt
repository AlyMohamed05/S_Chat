package com.example.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.core.database.entity.ChannelEntity
import com.example.core.database.entity.MessageEntity
import com.example.core.database.entity.UserInfoEntity

/**
 * (Full) here means that the channel has all it's data which is needed inside it like the user and last seen message
 */
data class ChannelFull(
    @Embedded val channel: ChannelEntity,
    @Relation(
        parentColumn = "friend_id",
        entityColumn = "id"
    )
    val friend: UserInfoEntity,
    @Relation(
        parentColumn = "last_message_id",
        entityColumn = "id"
    )
    val lastSeenMessage: MessageEntity?
)
