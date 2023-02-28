package com.silverbullet.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class NetworkUserInfo(
    val id: Int,
    val username: String,
    val name: String
)
