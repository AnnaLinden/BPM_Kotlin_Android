package com.example.graph_with_bluetooth


import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate

@Composable
fun HeartRateGraph(viewModel: HeartRateViewModel) {
    // Collect heart rate data from the ViewModel
    val heartRateData = viewModel.heartRateData.collectAsState()

    // Create the chart using MPAndroidChart
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        factory = { context ->
            LineChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                description.text = "Heart Rate (BPM)"
            }
        },
        update = { chart ->
            val entries = heartRateData.value.mapIndexed { index, heartRate ->
                Entry(index.toFloat(), heartRate.toFloat())
            }
            val dataSet = LineDataSet(entries, "Heart Rate").apply {
                setColors(*ColorTemplate.MATERIAL_COLORS)
                valueTextSize = 10f
            }
            chart.data = LineData(dataSet)
            chart.invalidate() // Refresh the chart
        }
    )
}