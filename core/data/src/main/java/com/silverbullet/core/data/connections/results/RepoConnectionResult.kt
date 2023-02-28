package com.silverbullet.core.data.connections.results

sealed interface RepoConnectionResult{

    object ConnectedSuccessfully: RepoConnectionResult

    object AlreadyConnected: RepoConnectionResult

    object UserNotFound: RepoConnectionResult

    object UnexpectedError: RepoConnectionResult
}