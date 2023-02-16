package com.silverbullet.schat.feature_auth.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedLock(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF33363F),
    locked: Boolean = true
) {
    val lockingLineScale by animateFloatAsState(
        targetValue = if (locked) 1f else 0f,
        animationSpec = tween(durationMillis = 750)
    )
    Canvas(
        modifier = modifier
            .size(48.dp)
    ) {

        drawRoundRect(
            color = color,
            topLeft = Offset(
                x = 8.dp.toPx(),
                y = 18.dp.toPx(),
            ),
            size = Size(
                width = 32.dp.toPx(),
                height = 24.dp.toPx()
            ),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx()),
            style = Stroke(width = 2.dp.toPx())
        )
        drawCircle(
            color = color,
            radius = 4.dp.toPx(),
            center = Offset(
                x = 24.dp.toPx(),
                y = 30.dp.toPx()
            )
        )
        val path = Path().apply {
            moveTo(
                x = 32.dp.toPx(),
                y = 16.dp.toPx()
            )
            lineTo(
                x = 32.dp.toPx(),
                y = 12.dp.toPx()
            )
            arcTo(
                startAngleDegrees = 0f,
                sweepAngleDegrees = -90f,
                rect = Rect(
                    topLeft = Offset(
                        26.dp.toPx(),
                        6.dp.toPx()
                    ),
                    bottomRight = Offset(
                        x = 32.dp.toPx(),
                        y = 12.dp.toPx()
                    )
                ),
                forceMoveTo = false
            )
            lineTo(
                x = 22.dp.toPx(),
                y = 6.dp.toPx()
            )
            arcTo(
                startAngleDegrees = -90f,
                sweepAngleDegrees = -90f,
                rect = Rect(
                    topLeft = Offset(
                        16.dp.toPx(),
                        6.dp.toPx()
                    ),
                    bottomRight = Offset(
                        x = 22.dp.toPx(),
                        y = 12.dp.toPx()
                    )
                ),
                forceMoveTo = false
            )

            lineTo(
                x = 16.dp.toPx(),
                y = 12.dp.toPx() + (4.dp.toPx() * lockingLineScale)
            )
        }
        drawPath(
            color = color,
            path = path,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun AnimatedLockPreview() {
    AnimatedLock()
}