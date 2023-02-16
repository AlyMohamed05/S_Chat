package com.silverbullet.schat.feature_home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.silverbullet.schat.R

@Composable
fun HomeTop(
    modifier: Modifier = Modifier,
    onShareClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Chats",
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onShareClick() }) {
            Icon(painter = painterResource(id = R.drawable.ic_share), contentDescription = null)
        }
        IconButton(onClick = { onAddClick() }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}