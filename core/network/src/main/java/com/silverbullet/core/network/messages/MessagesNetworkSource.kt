package com.silverbullet.core.network.messages

import com.silverbullet.core.network.utils.ChannelMessagesResult
import com.silverbullet.core.network.utils.MarkAsSeenResult
import com.silverbullet.core.network.utils.SendMessageResult

interface MessagesNetworkSource {

    suspend fun getChannelMessages(channelId: Int): ChannelMessagesResult

    /**
     * @param username user that will receive this message
     */
    suspend fun sendMessage(message: String, username: String): SendMessageResult

    suspend fun markAsSeen(messageId: String): MarkAsSeenResult
}