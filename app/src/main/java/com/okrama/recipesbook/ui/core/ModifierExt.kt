package com.okrama.recipesbook.ui.core

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Extension to create a custom shadow under component
 */
@Stable
fun Modifier.dropShadow(
    color: Color = Color.Black,
    elevation: Dp = 0.dp,
    cornerRadius: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (elevation != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(elevation.toPx(), BlurMaskFilter.Blur.NORMAL))
            }
            frameworkPaint.color = color.toArgb()

            val leftPixel = offsetX.toPx()
            val topPixel = offsetY.toPx()
            val rightPixel = size.width + leftPixel
            val bottomPixel = size.height + topPixel

            canvas.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                paint = paint,
                radiusX = cornerRadius.toPx(),
                radiusY = cornerRadius.toPx(),
            )
        }
    }
)

fun Modifier.bringIntoView(
    scrollState: ScrollState
): Modifier = composed {
    var scrollToPosition by remember {
        mutableStateOf(0f)
    }
    val coroutineScope = rememberCoroutineScope()
    this
        .onGloballyPositioned { coordinates ->
            scrollToPosition = scrollState.value + coordinates.positionInRoot().y
        }
        .onFocusEvent {
            if (it.isFocused) {
                coroutineScope.launch {
                    scrollState.animateScrollTo(scrollToPosition.toInt())
                }
            }
        }
}
