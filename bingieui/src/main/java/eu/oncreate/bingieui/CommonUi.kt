package eu.oncreate.bingieui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview

@Composable
fun BingieDivider(modifier: Modifier = Modifier) {
    Divider(color = MaterialTheme.colors.background, thickness = 1.dp, modifier = modifier)
}

@Composable
@Preview
fun BingieDivider() {
    Divider(color = MaterialTheme.colors.background, thickness = 1.dp)
}

@Composable
@Preview
fun CircleShapeDemo() {
    ExampleBox(shape = CircleShape)
}

@Composable
fun ExampleBox(shape: Shape) {
    Column(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
        Box(modifier = Modifier.preferredSize(100.dp).background(Color.Red, shape)) {
        }
    }
}
