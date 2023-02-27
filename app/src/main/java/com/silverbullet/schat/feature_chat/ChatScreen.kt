package com.silverbullet.schat.feature_chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.silverbullet.schat.feature_chat.components.ChatTop
import com.silverbullet.schat.core.ui.components.DefaultInputField
import com.silverbullet.schat.core.ui.theme.LocalSpacing
import com.silverbullet.schat.core.utils.PreviewData
import com.silverbullet.schat.R
import com.silverbullet.schat.feature_chat.components.MessageItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onCloseClick: () -> Unit
) {

    val chatState = viewModel.chatState.collectAsState()

    val message = viewModel.messageText.collectAsState()

    val systemKeyboard = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ChatTop(
                user = PreviewData.user,
                onCloseClick = onCloseClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
            )
            chatState.value?.let { chat ->
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = LocalSpacing.current.smallSpace)
                ) {
                    item { Spacer(modifier = Modifier.height(LocalSpacing.current.smallSpace)) }
                    items(chat.messages) { message ->
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            MessageItem(
                                message = message,
                                ownMessage = true,
                                modifier = Modifier
                                    .widthIn(max = LocalConfiguration.current.screenWidthDp.dp * 0.7f)
                                    .align(
                                        Alignment.TopEnd
                                    )
                            )
                        }
                        Spacer(modifier = Modifier.height(LocalSpacing.current.smallSpace))
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = LocalSpacing.current.mediumSpace)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DefaultInputField(
                text = message.value,
                onValueChange = viewModel::setMessageText,
                hint = stringResource(id = R.string.message_hint),
                modifier = Modifier
                    .animateContentSize()
                    .weight(1f)
            )
            AnimatedVisibility(visible = message.value.isNotBlank()) {
                IconButton(
                    onClick = {
                        systemKeyboard?.hide()
                        viewModel.send()
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