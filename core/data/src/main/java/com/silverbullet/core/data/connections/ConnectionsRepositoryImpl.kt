package com.silverbullet.core.data.connections

import com.silverbullet.core.data.connections.results.RepoConnectionResult
import com.silverbullet.core.data.utils.RepoResult
import com.silverbullet.core.network.connections.ConnectionsNetworkSource
import com.silverbullet.core.network.utils.ConnectionResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConnectionsRepositoryImpl @Inject constructor(
    private val connectionsNetworkSource: ConnectionsNetworkSource
) : ConnectionsRepository {

    override fun connectToUser(username: String): Flow<RepoResult<RepoConnectionResult>> =
        flow {
            emit(RepoResult.Loading())
            val repoResult = when (connectionsNetworkSource.connectWithUser(username)) {

                ConnectionResult.ConnectedSuccessfully -> RepoConnectionResult.ConnectedSuccessfully

                ConnectionResult.AlreadyConnected -> RepoConnectionResult.AlreadyConnected

                ConnectionResult.UserNotFound -> RepoConnectionResult.UserNotFound

                else -> RepoConnectionResult.UnexpectedError
            }
            emit(RepoResult.HasResult(repoResult))
        }
}