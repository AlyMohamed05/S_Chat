package com.silverbullet.schat.feature_chat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.silverbullet.schat.R
import com.silverbullet.schat.core.model.User
import com.silverbullet.schat.core.ui.theme.LocalSpacing

@Composable
fun ChatTop(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = {},
    height: Dp = 96.dp,
    user: User
) {
    Row(
        modifier = modifier
            .defaultMinSize(minHeight = height)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.face1),
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Color.Red)
        )
        Spacer(modifier = Modifier.width(LocalSpacing.current.mediumSpace))
        Column {
            Text(
                text = user.name,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(LocalSpacing.current.extraSmallSpace))
            if (user.online) {
                Text(
                    text = stringResource(id = R.string.online),
                    color = Color(0xFF0D7612),
                    style = MaterialTheme.typography.h5
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onCloseClick) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    }
}