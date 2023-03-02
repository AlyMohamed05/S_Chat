package com.silverbullet.core.network.messages.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    val receiverUsername: String,
    val text: String
)