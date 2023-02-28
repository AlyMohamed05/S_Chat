package com.silverbullet.core.network.connections.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ConnectionRequest(
    val username: String
)