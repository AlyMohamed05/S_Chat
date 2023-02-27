package com.silverbullet.schat.feature_chat

import androidx.lifecycle.ViewModel
import com.silverbullet.schat.core.model.Chat
import com.silverbullet.schat.core.utils.PreviewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    private val _chatState = MutableStateFlow<Chat?>(null)
    val chatState = _chatState.asStateFlow()

    private val _messageText = MutableStateFlow("")
    val messageText = _messageText.asStateFlow()

    init {
        _chatState.value = PreviewData.chat.copy(messages = PreviewData.generateFakeMessages())
    }

    fun setMessageText(value: String) {
        _messageText.value = value
    }

    fun send() {
        _messageText.value = ""
    }
}