package com.silverbullet.core.data.channels

import com.example.core.database.dao.ChannelDao
import com.example.core.database.model.ChannelFull
import com.silverbullet.core.data.mapper.toExternalModel
import com.silverbullet.core.model.ChannelInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChannelsRepositoryImpl @Inject constructor(
    private val channelsDao: ChannelDao,
) : ChannelsRepository {

    override val channels: Flow<List<ChannelInfo>>
        get() = channelsDao
            .getChannels()
            .map { it.map(ChannelFull::toExternalModel) }

}