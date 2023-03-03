package com.silverbullet.core.data.mapper

import com.example.core.database.entity.MessageEntity
import com.silverbullet.core.model.Message
import com.silverbullet.core.network.model.response.NetworkMessage

fun NetworkMessage.toMessageEntity(): MessageEntity =
    MessageEntity(
        senderId = senderId,
        receiverId = receiverId,
        text = text,
        channelId = channelId,
        seen = seen,
        id = id
    )

fun MessageEntity.toExternalModel(currentUserId: Int): Message =
    Message(
        senderId = senderId,
        receiverId = receiverId,
        channelId = channelId,
        text = text,
        seen = seen,
        isOwnMessage = senderId == currentUserId,
        id = id
    )