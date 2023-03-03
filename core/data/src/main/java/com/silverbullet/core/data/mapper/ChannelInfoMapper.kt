package com.silverbullet.core.data.mapper

import com.example.core.database.model.ChannelFull
import com.silverbullet.core.model.ChannelInfo

fun ChannelFull.toExternalModel(currentUserId: Int): ChannelInfo =
    ChannelInfo(
        id = channel.id,
        friend = friend.toExternalModel(),
        lastMessage = lastSeenMessage?.toExternalModel(currentUserId)
    )