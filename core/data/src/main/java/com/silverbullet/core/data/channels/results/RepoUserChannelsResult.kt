package com.silverbullet.core.data.channels.results


sealed interface RepoUserChannelsResult {

    object Loaded: RepoUserChannelsResult

    object Failed : RepoUserChannelsResult
}