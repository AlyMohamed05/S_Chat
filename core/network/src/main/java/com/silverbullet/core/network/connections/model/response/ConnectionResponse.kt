package com.silverbullet.core.network.connections.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ConnectionResponse(
    val status: Boolean
)