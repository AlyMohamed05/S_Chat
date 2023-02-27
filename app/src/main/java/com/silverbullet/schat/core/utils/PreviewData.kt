package com.silverbullet.schat.core.utils

import com.silverbullet.schat.core.model.Chat
import com.silverbullet.schat.core.model.Message
import com.silverbullet.schat.core.model.User

object PreviewData {

    val user = User(
        name = "Cathrine",
        photoUrl = null,
        online = true,
        id = 0
    )

    val user2 = User(
        name = "X User",
        photoUrl = null,
        online = true,
        id = 1
    )

    val messages = listOf(
        Message(
            text = "Message 1",
            senderId = user2.id,
            receiverId = user.id,
            timestamp = System.currentTimeMillis(),
            seen = true,
            id = 0
        ),
        Message(
            text = "Message 2",
            senderId = user2.id,
            receiverId = user.id,
            timestamp = System.currentTimeMillis() + 100,
            seen = false,
            id = 1
        )
    )

    val chat = Chat(
        user = user,
        id = 0,
        messages = messages
    )

    fun generateFakeMessages(): List<Message> {
        return buildList {
            repeat(100) {
                add(
                    Message(
                        text = "Message $it",
                        senderId = user2.id,
                        receiverId = user.id,
                        timestamp = System.currentTimeMillis() + 100,
                        seen = false,
                        id = 1
                    )
                )
            }
        }
    }


}