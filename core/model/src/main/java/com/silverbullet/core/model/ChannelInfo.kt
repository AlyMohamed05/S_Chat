package com.silverbullet.core.model

/**
 * @param friend the other user who is friend to this user
 */
data class ChannelInfo(
    val id: Int,
    val friend: UserInfo,
    val lastMessage: Message?
)
