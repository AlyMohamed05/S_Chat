package com.silverbullet.core.data.channels

import com.silverbullet.core.data.channels.results.RepoUserChannelsResult
import com.silverbullet.core.data.utils.RepoResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChannelsRepositoryImpl : ChannelsRepository {

    override suspend fun getUserChannels(): Flow<RepoResult<RepoUserChannelsResult>> =
        flow {
            // TODO: Fetch the user channels and show them
        }
}