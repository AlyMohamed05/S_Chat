package com.silverbullet.core.data.channels

import com.silverbullet.core.data.channels.results.RepoUserChannelsResult
import com.silverbullet.core.data.utils.RepoResult
import kotlinx.coroutines.flow.Flow

interface ChannelsRepository {

    suspend fun getUserChannels(): Flow<RepoResult<RepoUserChannelsResult>>
}