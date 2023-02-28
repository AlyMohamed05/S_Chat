package com.silverbullet.schat.feature_chat

import androidx.lifecycle.ViewModel
import com.silverbullet.core.model.ChannelInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    private val _channelState = MutableStateFlow<ChannelInfo?>(null)
    val channelState = _channelState.asStateFlow()

    private val _messageText = MutableStateFlow("")
    val messageText = _messageText.asStateFlow()

    fun setMessageText(value: String) {
        _messageText.value = value
    }

    fun send() {
        _messageText.value = ""
    }
}