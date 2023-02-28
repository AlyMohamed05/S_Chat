package com.silverbullet.core.model

data class ChannelInfo(
    val id: Int,
    val members: List<UserInfo>,
    val lastMessage: Message?
)
