package com.silverbullet.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class NetworkMessage(
    val senderId: Int,
    val receiverId: Int,
    val channelId: Int,
    val text: String,
    val seen: Boolean,
    val id: String
)
