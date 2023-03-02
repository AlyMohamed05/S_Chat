package com.silverbullet.core.network.messages.model.request

import kotlinx.serialization.Serializable

@Serializable
data class MarkAsSeenRequest(
    val messageId: String
)