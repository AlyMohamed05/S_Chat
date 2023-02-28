package com.silverbullet.core.network.channels.model

import com.silverbullet.core.network.model.response.NetworkMessage
import com.silverbullet.core.network.model.response.NetworkUserInfo
import kotlinx.serialization.Serializable

@Serializable
data class NetworkChannelInfo(
    val id: Int,
    val members: List<NetworkUserInfo>,
    val lastMessage: NetworkMessage?
)
