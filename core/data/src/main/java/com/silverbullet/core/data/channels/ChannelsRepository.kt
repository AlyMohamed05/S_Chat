package com.silverbullet.core.data.channels

import com.silverbullet.core.model.ChannelInfo
import kotlinx.coroutines.flow.Flow

interface ChannelsRepository {

    val channels: Flow<List<ChannelInfo>>

}