package com.silverbullet.schat.feature_chat

interface ChatScreenEvent {

    data class MessageFieldUpdated(val message: String): ChatScreenEvent

    object Send: ChatScreenEvent
}