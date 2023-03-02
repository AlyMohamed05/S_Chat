package com.example.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "t_channels",
    foreignKeys = [
        ForeignKey(
            entity = UserInfoEntity::class,
            parentColumns = ["id"],
            childColumns = ["friend_id"],
            onDelete = ForeignKey.CASCADE,
            deferred = true
        ),
        ForeignKey(
            entity = MessageEntity::class,
            parentColumns = ["id"],
            childColumns = ["last_message_id"],
            onDelete = ForeignKey.SET_NULL,
            deferred = true
        )
    ],
    indices = [
        Index(value = ["friend_id"]),
        Index(value = ["last_message_id"])
    ]
)
data class ChannelEntity(
    @ColumnInfo(name = "friend_id")
    val friendId: Int,
    @ColumnInfo(name = "last_message_id")
    val lastMessageId: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int
)
