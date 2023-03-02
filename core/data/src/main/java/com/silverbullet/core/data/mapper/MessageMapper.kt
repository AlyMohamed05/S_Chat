package com.silverbullet.core.data.mapper

import com.example.core.database.entity.MessageEntity
import com.silverbullet.core.model.Message

fun MessageEntity.toExternalModel(): Message =
    Message(
        senderId = senderId,
        receiverId = receiverId,
        channelId = channelId,
        text = text,
        seen = seen,
        id = id
    )