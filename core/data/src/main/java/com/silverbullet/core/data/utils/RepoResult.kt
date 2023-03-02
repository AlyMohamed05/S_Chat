package com.silverbullet.core.data.utils

sealed class RepoResult<T> {

    class HasResult<T>(val result: T) : RepoResult<T>()

    class Loading<T> : RepoResult<T>()
}
