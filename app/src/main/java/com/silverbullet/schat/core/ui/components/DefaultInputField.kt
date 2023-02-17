package com.silverbullet.schat.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.silverbullet.schat.core.ui.theme.LocalSpacing

@Composable
fun DefaultInputField(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.h4,
    hint: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    text: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier
            .defaultMinSize(minHeight = 56.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Color(0xFFEAEAEA))
    ) {
        Row(
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(leadingIcon!= null){
                leadingIcon()
                Spacer(modifier = Modifier.width(LocalSpacing.current.smallSpace))
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicTextField(
                    value = text,
                    onValueChange = onValueChange,
                    textStyle = textStyle,
                    modifier = Modifier.fillMaxSize()
                )
                if (text.isEmpty() && hint != null) {
                    Text(
                        text = hint,
                        style = textStyle,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
            }
        }
    }
}
