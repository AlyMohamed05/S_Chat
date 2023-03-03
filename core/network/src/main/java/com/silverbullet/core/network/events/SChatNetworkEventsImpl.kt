package com.silverbullet.core.network.events

import com.silverbullet.core.network.TokenController
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.header
import io.ktor.http.HttpMethod
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SChatNetworkEventsImpl @Inject constructor(
    private val ktorClient: HttpClient,
    @EventsJsonSerializer
    private val eventsSerializer: Json,
    private val tokenController: TokenController
) : SChatNetworkEvents {

    private val _event = MutableSharedFlow<Event>()
    override val events: SharedFlow<Event> = _event.asSharedFlow()

    private var websocket: DefaultClientWebSocketSession? = null

    override suspend fun listenToEvents() {
        val token = tokenController.getAccessToken() ?: return
        ktorClient
            .webSocket(
                method = HttpMethod.Get,
                host = "192.168.1.3",
                port = 8080,
                path = "events",
                request = {
                    this.header("Authorization", "Bearer $token")
                }
            ) {
                websocket = this
                incoming
                    .consumeAsFlow()
                    .collect { frame ->
                        if (frame is Frame.Text) {
                            val json = frame.readText()
                            val event = eventsSerializer.decodeFromString<Event>(json)
                            _event.emit(event)
                        }
                    }
            }
    }

    override suspend fun stopListening() {
        websocket?.close(CloseReason(CloseReason.Codes.GOING_AWAY, "CLOSE"))
    }
}
