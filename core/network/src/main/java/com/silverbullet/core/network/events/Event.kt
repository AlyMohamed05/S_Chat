package com.silverbullet.core.network.events

import com.silverbullet.core.network.model.response.NetworkMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface Event

@Serializable
@SerialName("added_to_channel")
data class AddedToChannelEvent(
    val channelId: Int
): Event

@Serializable
@SerialName("received_message")
data class ReceivedMessage(
    val message: NetworkMessage
): Event

@Serializable
@SerialName("online_status")
data class OnlineStatus(
    val userId: Int,
    val isOnline: Boolean
): Event

@Serializable
@SerialName("message_updated")
data class UpdatedMessage(
    val message: NetworkMessage
): Event