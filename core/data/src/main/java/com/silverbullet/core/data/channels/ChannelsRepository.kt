package com.silverbullet.core.data.channels

import com.silverbullet.core.data.utils.Synchronizable
import com.silverbullet.core.model.ChannelInfo
import kotlinx.coroutines.flow.Flow

interface ChannelsRepository: Synchronizable {

    val channels: Flow<List<ChannelInfo>>

}