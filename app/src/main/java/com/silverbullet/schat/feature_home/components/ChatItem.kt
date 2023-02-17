package com.silverbullet.schat.feature_home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silverbullet.schat.core.model.Chat
import com.silverbullet.schat.core.ui.theme.LocalSpacing
import com.silverbullet.schat.core.utils.PreviewData
import com.silverbullet.schat.R
import com.silverbullet.schat.core.model.Message
import com.silverbullet.schat.core.ui.theme.Montserrat

@Composable
fun ChatItem(
    modifier: Modifier = Modifier,
    height: Dp = 76.dp,
    chat: Chat,
    isMyMessage: (Message) -> Boolean,
    onClick: (Chat) -> Unit
) {

    // The last sent message in the chat
    val message = chat.messages[chat.messages.lastIndex]

    Box(
        modifier = modifier
            .defaultMinSize(minHeight = height)
            .clickable { onClick(chat) }
            .padding(horizontal = LocalSpacing.current.mediumSpace)
    ) {
        Row(modifier = Modifier.matchParentSize()) {
            // Profile Pic
            Image(
                painter = painterResource(id = R.drawable.face1),
                contentDescription = null,
                modifier = Modifier.size(72.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
            )
            Spacer(modifier = Modifier.width(LocalSpacing.current.mediumSpace))
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(top = LocalSpacing.current.smallSpace),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = chat.user.name,
                        style = MaterialTheme.typography.h3
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = if (message.seen) painterResource(id = R.drawable.ic_seen)
                            else
                                painterResource(id = R.drawable.ic_sent),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(LocalSpacing.current.extraSmallSpace))
                        Text(
                            text = "12:00",
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
                Spacer(modifier = Modifier.height(LocalSpacing.current.extraSmallSpace))
                Text(
                    text = buildAnnotatedString {
                        if (isMyMessage(message)) {
                            withStyle(
                                SpanStyle(
                                    color = Color(0xFF939393),
                                    fontSize = 14.sp,
                                    fontFamily = Montserrat,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("You: ")
                            }
                        }
                        withStyle(SpanStyle(color = Color.Black, fontSize = 14.sp)) {
                            append(message.text)
                        }
                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatItemPreview() {
    ChatItem(
        chat = PreviewData.chat,
        onClick = {},
        isMyMessage = { true },
        modifier = Modifier.fillMaxWidth()
    )
}