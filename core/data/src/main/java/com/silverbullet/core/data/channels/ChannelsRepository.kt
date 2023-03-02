package com.silverbullet.core.data.channels

import com.silverbullet.core.data.channels.results.RepoUserChannelsResult
import com.silverbullet.core.data.utils.RepoResult
import com.silverbullet.core.model.ChannelInfo
import kotlinx.coroutines.flow.Flow

interface ChannelsRepository {

    val channels: Flow<List<ChannelInfo>>

    suspend fun getUserChannels(): Flow<RepoResult<RepoUserChannelsResult>>
}