package com.silverbullet.core.data.events

import com.example.core.database.dao.ChannelDao
import com.example.core.database.dao.MessagesDao
import com.silverbullet.core.data.mapper.toMessageEntity
import com.silverbullet.core.network.events.ReceivedMessage
import com.silverbullet.core.network.events.SChatNetworkEvents
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val networkEvents: SChatNetworkEvents,
    private val messagesDao: MessagesDao,
    private val channelDao: ChannelDao
) : EventsRepository {

    override suspend fun startEventHandler() {
        coroutineScope {
            launch {
                networkEvents
                    .listenToEvents()
            }
            networkEvents
                .events
                .collect { event ->
                    when (event) {
                        is ReceivedMessage -> {
                            val messageEntity = event.message.toMessageEntity()
                            messagesDao.insertMessage(messageEntity)
                            channelDao.updateLastMessageForChannel(
                                channelId = messageEntity.channelId,
                                lastMessageId = messageEntity.id
                            )
                        }

                        else -> Unit
                    }

                }
        }
    }

    override suspend fun endEventHandler() {
        networkEvents.stopListening()
    }
}