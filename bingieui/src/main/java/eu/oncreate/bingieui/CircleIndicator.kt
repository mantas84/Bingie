package eu.oncreate.bingieui

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.animation.transition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview

private val AngleFactor = FloatPropKey()

@Composable
@Preview
fun CircleIndicator(percentage: Float = 93.3f, size: Int = 100) {
    Box() {
        DrawCircles(percentage = percentage, size = size)
        Box(modifier = Modifier.align(Alignment.Center)) {
            Text(text = percentage.toString(), color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawCircles(percentage: Float = 75f, size: Int = 100) {

    val state = transition(
        definition = CircularTransition,
        initState = AnimatedCircleProgress.START,
        toState = AnimatedCircleProgress.END
    )

    Box(modifier = Modifier.background(Color.Black, CircleShape)) {

        val angle = 3.6f * percentage
        val arcSize = (size * 0.9f).dp
        val offsetSize = (size * 0.05f).dp
        val strokeWidth = size * 0.12f
//        val stroke = with(DensityAmbient.current) { Stroke(5.dp.toPx()) }

        Canvas(modifier = Modifier.width(100.dp).height(100.dp)) {

            val progressAngle = angle * state[AngleFactor]

            drawArc(
                color = Color.Blue,
                startAngle = 270f,
                sweepAngle = progressAngle,
                useCenter = false,
                size = Size(arcSize.toPx(), arcSize.toPx()),
                topLeft = Offset(offsetSize.toPx(), offsetSize.toPx()),
                style = Stroke(width = strokeWidth)
            )

            drawArc(
                color = Color.Gray,
                startAngle = 270f + progressAngle,
                sweepAngle = 360f - progressAngle,
                useCenter = false,
                size = Size(arcSize.toPx(), arcSize.toPx()),
                topLeft = Offset(offsetSize.toPx(), offsetSize.toPx()),
                style = Stroke(width = strokeWidth)
            )

        }

    }

}

private enum class AnimatedCircleProgress { START, END }

private val CircularTransition = transitionDefinition<AnimatedCircleProgress> {
    state(AnimatedCircleProgress.START) {
        this[AngleFactor] = 0f
    }
    state(AnimatedCircleProgress.END) {
        this[AngleFactor] = 1f
    }
    transition(fromState = AnimatedCircleProgress.START, toState = AnimatedCircleProgress.END) {
        AngleFactor using tween(
            delayMillis = 500,
            durationMillis = 900,
            easing = CubicBezierEasing(0f, 0.75f, 0.35f, 0.85f)
        )
    }
}
