package com.silverbullet.core.data.connections

import com.silverbullet.core.data.connections.results.RepoConnectionResult
import com.silverbullet.core.data.utils.RepoResult
import kotlinx.coroutines.flow.Flow

interface ConnectionsRepository {

    fun connectToUser(username: String): Flow<RepoResult<RepoConnectionResult>>
}