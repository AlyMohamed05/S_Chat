package com.silverbullet.core.data.channels.results

import com.silverbullet.core.model.ChannelInfo

sealed interface RepoUserChannelsResult {

    data class Channels(
        val channels: List<ChannelInfo>
    ) : RepoUserChannelsResult

    object Failed : RepoUserChannelsResult
}