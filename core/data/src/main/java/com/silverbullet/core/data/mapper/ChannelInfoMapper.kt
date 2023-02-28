package com.silverbullet.core.data.mapper

import com.silverbullet.core.model.ChannelInfo
import com.silverbullet.core.network.channels.model.NetworkChannelInfo

/**
 * @param currentUserId is the current user id of the app user.
 */
fun NetworkChannelInfo.toChannelInfo(currentUserId: Int): ChannelInfo =
    ChannelInfo(
        id = id,
        friend = members.find { it.id != currentUserId }!!.toUserInfo(),
        lastMessage = lastMessage?.toMessage()
    )