package com.silverbullet.schat.feature_chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.silverbullet.schat.feature_chat.components.ChatTop
import com.silverbullet.schat.core.ui.components.DefaultInputField
import com.silverbullet.schat.core.ui.theme.LocalSpacing
import com.silverbullet.schat.R
import com.silverbullet.schat.feature_chat.components.MessageItem

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onCloseClick: () -> Unit
) {

    val message = viewModel.messageText.collectAsState()

    val channelMessages = viewModel.channelMessages.collectAsState()

    val messagesListState = rememberLazyListState()
    LaunchedEffect(key1 = Unit) {
        messagesListState
            .scrollToItem(channelMessages.value.lastIndex.asLastMessageIndex())
    }
    LaunchedEffect(key1 = channelMessages.value.size) {
        messagesListState
            .animateScrollToItem(channelMessages.value.lastIndex.asLastMessageIndex())
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ChatTop(
                username = viewModel.username,
                onCloseClick = onCloseClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
            )
            val screenWidthDb = LocalConfiguration.current.screenWidthDp.dp
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = LocalSpacing.current.smallSpace),
                state = messagesListState
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                items(channelMessages.value) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        MessageItem(
                            message = it,
                            ownMessage = it.isOwnMessage,
                            modifier = Modifier
                                .widthIn(max = screenWidthDb * 0.6f)
                                .wrapContentHeight()
                                .align(if (it.isOwnMessage) Alignment.CenterEnd else Alignment.CenterStart)
                        )
                    }
                    Spacer(modifier = Modifier.height(LocalSpacing.current.smallSpace))
                }
                item { Spacer(modifier = Modifier.height(48.dp)) }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DefaultInputField(
                text = message.value,
                onValueChange = { viewModel.onEvent(ChatScreenEvent.MessageFieldUpdated(it)) },
                hint = stringResource(id = R.string.message_hint),
                modifier = Modifier
                    .animateContentSize()
                    .weight(1f)
            )
            AnimatedVisibility(visible = message.value.isNotBlank()) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(ChatScreenEvent.Send)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

private fun Int.asLastMessageIndex(): Int {
    if (this > 0)
        return this
    return 0
}