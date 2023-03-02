package com.silverbullet.core.data.messages

import com.example.core.database.dao.MessagesDao
import com.example.core.database.entity.MessageEntity
import com.silverbullet.core.data.mapper.toExternalModel
import com.silverbullet.core.data.messages.results.RepoMarkAsSeenResult
import com.silverbullet.core.data.messages.results.RepoSendMessageResult
import com.silverbullet.core.data.utils.RepoResult
import com.silverbullet.core.model.Message
import com.silverbullet.core.network.messages.MessagesNetworkSource
import com.silverbullet.core.network.utils.MarkAsSeenResult
import com.silverbullet.core.network.utils.SendMessageResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor(
    private val messagesNetworkSource: MessagesNetworkSource,
    private val messagesDao: MessagesDao
) : MessagesRepository {

    override fun getChannelMessages(channelId: Int): Flow<List<Message>> =
        messagesDao
            .getChannelMessages(channelId)
            .map { it.map(MessageEntity::toExternalModel) }

    override fun sendMessage(
        username: String,
        message: String
    ): Flow<RepoResult<RepoSendMessageResult>> =
        flow {
            emit(RepoResult.Loading())
            val repoResult =
                when (messagesNetworkSource.sendMessage(message = message, username = username)) {

                    SendMessageResult.Sent -> RepoSendMessageResult.Sent

                    SendMessageResult.UserNotFound -> RepoSendMessageResult.UserNotFound

                    SendMessageResult.NotConnectedToUser -> RepoSendMessageResult.NotConnectedToUser

                    else -> RepoSendMessageResult.UnexpectedError
                }
            emit(RepoResult.HasResult(repoResult))
        }

    override fun markAsSeen(messageId: String): Flow<RepoResult<RepoMarkAsSeenResult>> =
        flow {
            emit(RepoResult.Loading())
            val repoResult =
                when (messagesNetworkSource.markAsSeen(messageId)) {

                    MarkAsSeenResult.Successful -> RepoMarkAsSeenResult.Successful

                    MarkAsSeenResult.MessageNotFound -> RepoMarkAsSeenResult.MessageNotFound

                    else -> RepoMarkAsSeenResult.UnexpectedError
                }
            emit(RepoResult.HasResult(repoResult))
        }
}