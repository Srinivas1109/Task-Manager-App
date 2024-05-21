package com.benki.taskmanager.presentation.report

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.util.fastMaxOfOrNull
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.benki.taskmanager.data.model.BarData
import com.benki.taskmanager.presentation.components.BarChart

@Composable
fun ReportScreen(modifier: Modifier = Modifier, viewModel: ReportViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val barChartData =
        uiState.tasksWithStatus.filter { it.value > 0 }.map {
            BarData(label = it.key.statusText, value = it.value, barColor = it.key.color)
        }
    Box(
        modifier = modifier
            .safeDrawingPadding()
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BarChart(
            modifier = modifier,
            data = barChartData,
            maxRange = barChartData.fastMaxOfOrNull { it.value }?.toFloat() ?: 30f,
        )
    }
}