package com.silverbullet.schat.feature_auth.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
    borderColor: Color = Color(0xFF252836),
    value: String,
    onValueChanged: (String) -> Unit,
) {

    var hintSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    Box(
        modifier = Modifier
            .padding(vertical = with(LocalDensity.current) { hintSize.height.toDp() / 2 }),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .defaultMinSize(minHeight = 48.dp)
                .drawBehind {
                    drawInputFieldBroder(
                        24.dp,
                        hintWidth = hintSize.width.toFloat(),
                        borderColor = borderColor
                    )
                }
        ) {
            Text(
                text = label ?: "",
                style = labelStyle,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 8.dp)
                    .offset(
                        x = 4.dp,
                        y = -with(LocalDensity.current) { hintSize.height.toDp() / 2 })
                    .onGloballyPositioned { hintSize = it.size }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.Center)
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
                Spacer(modifier = Modifier.width(8.dp))
                trailingIcon()
            }
        }
    }
}

private fun DrawScope.drawInputFieldBroder(
    borderRadius: Dp,
    borderColor: Color,
    hintWidth: Float = 0f
) {
    val width = size.width
    val height = size.height

    val borderWidth = 2.dp.toPx()

    val radius = borderRadius.toPx()

    val stroke = Stroke(width = borderWidth, cap = StrokeCap.Round)
    val arcSize = Size(radius * 2f, radius * 2f)

    Path().apply {
        moveTo(radius + hintWidth -4.dp.toPx(), 0f)
        lineTo(width - radius, 0f)
        drawPath(this, color = borderColor, style = stroke)

        reset()
        moveTo(width, radius)
        lineTo(width, height - radius)
        drawPath(this, color = borderColor, style = stroke)

        reset()
        moveTo(width - radius, height)
        lineTo(radius, height)
        drawPath(this, color = borderColor, style = stroke)

        reset()
        moveTo(0f, height - radius)
        lineTo(0f, radius)
        drawPath(this, color = borderColor, style = stroke)

        drawArc(
            color = borderColor,
            startAngle = -90f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(width - (radius * 2f), 0f),
            size = arcSize,
            style = stroke
        )
        drawArc(
            color = borderColor,
            startAngle = 0f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(width - (radius * 2f), height - (radius * 2f)),
            size = arcSize,
            style = stroke
        )
        drawArc(
            color = borderColor,
            startAngle = 90f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(0f, height - (radius * 2f)),
            size = arcSize,
            style = stroke
        )
        drawArc(
            color = borderColor,
            startAngle = 180f,
            sweepAngle = 45f,
            useCenter = false,
            topLeft = Offset.Zero,
            size = arcSize,
            style = stroke
        )
    }
}