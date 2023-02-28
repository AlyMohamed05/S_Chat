package com.silverbullet.core.data.channels

import com.silverbullet.core.data.channels.results.RepoUserChannelsResult
import com.silverbullet.core.data.mapper.toChannelInfo
import com.silverbullet.core.data.preferences.Preferences
import com.silverbullet.core.data.utils.RepoResult
import com.silverbullet.core.network.channels.ChannelsNetworkSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChannelsRepositoryImpl @Inject constructor(
    private val channelsNetworkSource: ChannelsNetworkSource,
    private val preferences: Preferences
) : ChannelsRepository {

    private val userInfo = preferences.loadUserInfo()!!

    override suspend fun getUserChannels(): Flow<RepoResult<RepoUserChannelsResult>> =
        flow {
            emit(RepoResult.Loading())
            val userChannelsNetworkResult = channelsNetworkSource.getUserChannels()
            if (userChannelsNetworkResult == null) {
                emit(RepoResult.HasResult(RepoUserChannelsResult.Failed))
            } else {
                // Then the channels has been fetched
                val userChannels = userChannelsNetworkResult.map {
                    it.toChannelInfo(userInfo.id)
                }
                emit(
                    RepoResult.HasResult(
                        RepoUserChannelsResult.Channels(userChannels)
                    )
                )
            }
        }
}