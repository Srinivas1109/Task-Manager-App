package com.benki.taskmanager.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.benki.taskmanager.data.constants.TaskStatus
import com.benki.taskmanager.data.model.BarData
import com.benki.taskmanager.ui.theme.TaskManagerTheme

@Composable
fun Bar(
    modifier: Modifier = Modifier,
    barColor: Color = MaterialTheme.colorScheme.primary,
    barWidth: Dp = 40.dp,
    barHeight: Float,
    canvasHeight: Dp = 200.dp,
    spacing: Float = 4f,
    label: String = "",
    labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    var barVisible by remember {
        mutableStateOf(false)
    }
    val height by animateFloatAsState(
        targetValue = if (barVisible) barHeight else 0f,
        animationSpec = tween(1000),
        label = "bar_height"
    )
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = modifier
            .width(barWidth)
            .height(canvasHeight)
            .padding(4.dp)
    ) {
        drawText(
            textMeasurer = textMeasurer,
            text = label,
            topLeft = Offset(barWidth.value / 2f, canvasHeight.toPx() - height - 50f),
            style = TextStyle(color = labelColor)
        )
        drawRoundRect(
            color = barColor,
            topLeft = Offset(0f, canvasHeight.toPx() - height),
            cornerRadius = CornerRadius(10f),
            size = Size(width = barWidth.toPx() - 2 * spacing, height = height)
        )
    }

    LaunchedEffect(key1 = Unit) {
        barVisible = true
    }
}

@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    data: List<BarData> = emptyList(),
    maxRange: Float = 50f,
    barWidth: Dp = 40.dp,
    height: Dp = 150.dp
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.small
        ) {
            LazyRow(modifier = modifier
                .fillMaxWidth()
                .height(height)) {
                items(items = data) { barData ->
                    Bar(
                        barWidth = barWidth,
                        barHeight = (barData.value / maxRange) * height.value,
                        canvasHeight = height,
                        spacing = 16f,
                        label = barData.value.toString(),
                        barColor = barData.barColor
                    )
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(120.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
            items(items = TaskStatus.entries) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = modifier
                            .size(5.dp)
                            .background(color = it.color, shape = RectangleShape)
                    )
                    Text(text = it.statusText, maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = MaterialTheme.typography.bodySmall.fontSize)
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE,
)
@Composable
fun BarChartPreview(modifier: Modifier = Modifier) {
    TaskManagerTheme(darkTheme = true) {
        Box(modifier = modifier.fillMaxSize()) {
            BarChart(modifier = modifier.fillMaxHeight(0.5f))
        }
    }
}