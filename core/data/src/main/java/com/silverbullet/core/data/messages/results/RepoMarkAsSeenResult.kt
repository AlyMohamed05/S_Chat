package com.silverbullet.core.data.messages.results

interface RepoMarkAsSeenResult {

    object Successful : RepoMarkAsSeenResult

    object MessageNotFound : RepoMarkAsSeenResult

    object UnexpectedError : RepoMarkAsSeenResult
}