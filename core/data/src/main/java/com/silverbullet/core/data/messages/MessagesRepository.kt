package com.silverbullet.core.data.messages

import com.silverbullet.core.data.messages.results.RepoMarkAsSeenResult
import com.silverbullet.core.data.messages.results.RepoSendMessageResult
import com.silverbullet.core.data.utils.RepoResult
import com.silverbullet.core.model.Message
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {


    fun getChannelMessages(channelId: Int): Flow<List<Message>>

    fun sendMessage(username: String,message: String): Flow<RepoResult<RepoSendMessageResult>>

    fun markAsSeen(messageId: String): Flow<RepoResult<RepoMarkAsSeenResult>>
}