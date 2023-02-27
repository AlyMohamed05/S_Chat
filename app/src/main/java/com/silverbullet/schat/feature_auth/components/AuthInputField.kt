package com.silverbullet.schat.feature_auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

@Composable
fun AuthInputField(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    label: String? = null,
    labelStyle: TextStyle = TextStyle.Default,
    trailingIcon: @Composable () -> Unit = {},
    singleLine: Boolean = false,
    hideInput: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    borderColor: Color = MaterialTheme.colors.onSurface,
    hasError: Boolean = false,
    error: String? = null,
    onValueChanged: (String) -> Unit,
    value: String
) {
    Column(modifier = Modifier) {
        Box(modifier = modifier.padding(top = 4.dp)) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = if (hasError) Color.Red else borderColor,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(vertical = 18.dp, horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChanged,
                        textStyle = textStyle,
                        singleLine = singleLine,
                        keyboardActions = keyboardActions,
                        keyboardOptions = keyboardOptions,
                        visualTransformation = if (hideInput) PasswordVisualTransformation() else VisualTransformation.None,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    trailingIcon()
                }

            }
            if (label != null) {
                Text(
                    text = label,
                    style = labelStyle,
                    color = if (hasError) Color.Red else Color.Unspecified,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(12.dp, y = (-8).dp)
                        .background(MaterialTheme.colors.surface)
                        .padding(horizontal = 4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        if (hasError && error != null) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

@Preview(
    name = "NetInputField",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun NetInputFieldPreview() {
    AuthInputField(
        value = "Just some text",
        label = "Label",
        onValueChanged = {},
        trailingIcon = {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        },
        hasError = true,
        error = "email not valid",
        modifier = Modifier.width(250.dp)
    )
}