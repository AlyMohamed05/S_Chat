package com.silverbullet.core.data.channels

import com.example.core.database.dao.ChannelDao
import com.example.core.database.dao.ChannelFullDao
import com.example.core.database.dao.UserInfoDao
import com.example.core.database.model.ChannelFull
import com.silverbullet.core.data.channels.results.RepoUserChannelsResult
import com.silverbullet.core.data.mapper.toChannelFullEntity
import com.silverbullet.core.data.mapper.toExternalModel
import com.silverbullet.core.data.preferences.Preferences
import com.silverbullet.core.data.utils.RepoResult
import com.silverbullet.core.model.ChannelInfo
import com.silverbullet.core.network.channels.ChannelsNetworkSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChannelsRepositoryImpl @Inject constructor(
    private val channelsNetworkSource: ChannelsNetworkSource,
    private val channelsDao: ChannelDao,
    private val channelFullDao: ChannelFullDao,
    private val usersInfoDao: UserInfoDao,
    private val preferences: Preferences
) : ChannelsRepository {

    private val userInfo = preferences.loadUserInfo()!!

    override val channels: Flow<List<ChannelInfo>>
        get() = channelsDao
            .getChannels()
            .map { it.map(ChannelFull::toExternalModel) }

    override suspend fun getUserChannels(): Flow<RepoResult<RepoUserChannelsResult>> =
        flow {
            emit(RepoResult.Loading())
            val userChannelsNetworkResult = channelsNetworkSource.getUserChannels()
            if (userChannelsNetworkResult == null) {
                emit(RepoResult.HasResult(RepoUserChannelsResult.Failed))
            } else {
                emit(RepoResult.HasResult(RepoUserChannelsResult.Loaded))
                userChannelsNetworkResult.forEach { networkChannel ->
                    val channelFullEntity = networkChannel.toChannelFullEntity(userInfo.id)
                    channelFullDao.insertChannelFull(channelFullEntity)
                }
            }
        }
}