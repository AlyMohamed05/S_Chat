package com.silverbullet.core.network.events

import kotlinx.coroutines.flow.SharedFlow

interface SChatNetworkEvents {

    val events: SharedFlow<Event>

    suspend fun listenToEvents()

    suspend fun stopListening()

}