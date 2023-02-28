package com.silverbullet.core.data.mapper

import com.silverbullet.core.model.Message
import com.silverbullet.core.network.model.response.NetworkMessage

fun NetworkMessage.toMessage(): Message =
    Message(
        senderId = senderId,
        receiverId = receiverId,
        channelId = channelId,
        text = text,
        seen = seen,
        id = id
    )