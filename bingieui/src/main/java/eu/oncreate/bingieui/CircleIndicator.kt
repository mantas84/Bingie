package eu.oncreate.bingieui

import androidx.compose.foundation.Box
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Stack
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
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview

@Composable
@Preview
fun CircleIndicator(percentage: Float = 93.3f, size: Int = 100) {
    Stack() {
        DrawCircles(percentage = percentage, size = size)
        Box(modifier = Modifier.gravity(Alignment.Center)) {
            Text(text = percentage.toString(), color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawCircles(percentage: Float = 75f, size: Int = 100) {

    Box(backgroundColor = Color.Black, shape = CircleShape) {

        val angle = 3.6f * percentage
        val arcSize = (size * 0.9f).dp
        val offsetSize = (size * 0.05f).dp
        val strokeWidth = size * 0.12f
//        val stroke = with(DensityAmbient.current) { Stroke(5.dp.toPx()) }

        Canvas(modifier = Modifier.width(100.dp).height(100.dp)) {

            drawArc(
                Color.Blue,
                270f,
                angle,
                useCenter = false,
                size = Size(arcSize.toPx(), arcSize.toPx()),
                topLeft = Offset(offsetSize.toPx(), offsetSize.toPx()),
                style = Stroke(width = strokeWidth)
            )

            drawArc(
                Color.Gray,
                270f + angle,
                360f - angle,
                useCenter = false,
                size = Size(arcSize.toPx(), arcSize.toPx()),
                topLeft = Offset(offsetSize.toPx(), offsetSize.toPx()),
                style = Stroke(width = strokeWidth)
            )

        }

    }

}
