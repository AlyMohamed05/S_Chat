package com.silverbullet.core.data.utils

sealed class RepoResult<T>(result: T?) {

    class HasResult<T>(val result: T) : RepoResult<T>(result)

    class Loading<T> : RepoResult<T>(null)
}
