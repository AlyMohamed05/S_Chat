package com.silverbullet.core.data.channels

import com.example.core.database.dao.ChannelDao
import com.example.core.database.dao.ChannelFullDao
import com.silverbullet.core.data.mapper.toChannelFullEntity
import com.silverbullet.core.data.mapper.toExternalModel
import com.silverbullet.core.data.preferences.Preferences
import com.silverbullet.core.model.ChannelInfo
import com.silverbullet.core.network.channels.ChannelsNetworkSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChannelsRepositoryImpl @Inject constructor(
    private val channelsNetworkSource: ChannelsNetworkSource,
    private val channelsDao: ChannelDao,
    private val channelFullDao: ChannelFullDao,
    private val preferences: Preferences
) : ChannelsRepository {

    private val userInfo = preferences.loadUserInfo()!!

    override val channels: Flow<List<ChannelInfo>>
        get() = channelsDao
            .getChannels()
            .map { it.map { channel -> channel.toExternalModel(userInfo.id) } }

    override suspend fun synchronize() {
        val channelsNetworkResult = channelsNetworkSource
            .getUserChannels() ?: return
        val currentUserId = preferences.loadUserInfo()?.id ?: return
        val channelEntities = channelsNetworkResult.map { networkChannel ->
            networkChannel.toChannelFullEntity(currentUserId)
        }
        channelEntities.forEach { channel -> channelFullDao.insertChannelFull(channel) }
    }
}