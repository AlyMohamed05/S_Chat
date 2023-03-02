package com.silverbullet.schat.feature_chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverbullet.core.data.messages.MessagesRepository
import com.silverbullet.core.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val messagesRepository: MessagesRepository
) : ViewModel() {

    val channelId: Int = requireNotNull(savedStateHandle["channelId"])
    val username: String = requireNotNull(savedStateHandle["username"])

    private val _messageText = MutableStateFlow("")
    val messageText = _messageText.asStateFlow()

    private val _channelMessages = MutableStateFlow<List<Message>>(emptyList())
    val channelMessages = _channelMessages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            messagesRepository
                .getChannelMessages(channelId)
                .collect { messages ->
                    _channelMessages.value = messages
                }
        }
    }

    fun onEvent(event: ChatScreenEvent) {
        when (event) {

            is ChatScreenEvent.MessageFieldUpdated -> _messageText.value = event.message

            ChatScreenEvent.Send -> send()
        }
    }

    private fun send() {
        viewModelScope.launch {
            messagesRepository
                .sendMessage(
                    username = username,
                    message = _messageText.value
                )
                .collect {}
        }
        _messageText.value = ""
    }
}