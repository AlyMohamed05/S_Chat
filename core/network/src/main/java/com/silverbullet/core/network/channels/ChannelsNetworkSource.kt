package com.silverbullet.core.network.channels

import com.silverbullet.core.network.channels.model.NetworkChannelInfo

interface ChannelsNetworkSource {

    suspend fun getUserChannels(): List<NetworkChannelInfo>?
}