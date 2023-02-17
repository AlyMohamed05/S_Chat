package com.silverbullet.schat.core.model

data class Chat(
    val user: User,
    val messages: List<Message>,
    val id: Long
)
