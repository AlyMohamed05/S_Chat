package com.silverbullet.core.data.mapper

import com.example.core.database.entity.ChannelEntity
import com.example.core.database.entity.MessageEntity
import com.example.core.database.entity.UserInfoEntity
import com.example.core.database.model.ChannelFull
import com.silverbullet.core.network.channels.model.NetworkChannelInfo

fun NetworkChannelInfo.toChannelFullEntity(currentUserId: Int): ChannelFull {
    val friend = members.find { it.id != currentUserId }!!
    val friendInfo = UserInfoEntity(
        username = friend.username,
        name = friend.name,
        id = friend.id
    )
    val lastMessageEntity = lastMessage?.let {
        MessageEntity(
            senderId = it.senderId,
            receiverId = it.receiverId,
            text = it.text,
            channelId = it.channelId,
            seen = it.seen,
            id = it.id
        )
    }
    val channel = ChannelEntity(
        friendId = friendInfo.id,
        lastMessageId = lastMessageEntity?.id,
        id = this.id
    )
    return ChannelFull(
        channel = channel,
        friend = friendInfo,
        lastSeenMessage = lastMessageEntity
    )
}