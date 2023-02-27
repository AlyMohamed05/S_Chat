package com.silverbullet.schat.feature_chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.silverbullet.schat.core.model.Message

@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    borderRadius: Dp = 32.dp,
    message: Message,
    ownMessage: Boolean
) {

    val boxShape = RoundedCornerShape(
        topStart = borderRadius,
        topEnd = borderRadius,
        bottomStart = if (!ownMessage) 0.dp else 32.dp,
        bottomEnd = if (ownMessage) 0.dp else 32.dp
    )

    Box(
        modifier = modifier
            .clip(boxShape)
            .background(if (MaterialTheme.colors.isLight) Color.White else Color(0xFFABB0B8))
            .padding(12.dp)
    ) {
        Text(
            text = message.text,
            style = MaterialTheme.typography.h5
        )
    }
}