package com.silverbullet.core.model

data class Message(
    val senderId: Int,
    val receiverId: Int,
    val channelId: Int,
    val text: String,
    val seen: Boolean,
    val id: String
)