package com.silverbullet.core.network.connections

import com.silverbullet.core.network.utils.ConnectionResult

interface ConnectionsNetworkSource {

    suspend fun connectWithUser(username: String): ConnectionResult
}