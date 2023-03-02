package com.silverbullet.core.data.messages.results

interface RepoSendMessageResult {

    object Sent : RepoSendMessageResult

    object UserNotFound : RepoSendMessageResult

    object NotConnectedToUser : RepoSendMessageResult

    object UnexpectedError : RepoSendMessageResult
}