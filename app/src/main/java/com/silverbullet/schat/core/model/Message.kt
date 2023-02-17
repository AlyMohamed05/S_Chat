package com.silverbullet.schat.core.model

data class Message(
    val text: String,
    val senderId: Long,
    val receiverId: Long,
    val timestamp: Long,
    val seen: Boolean,
    val id: Long
)
