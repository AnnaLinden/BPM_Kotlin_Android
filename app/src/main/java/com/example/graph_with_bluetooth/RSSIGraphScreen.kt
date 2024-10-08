package com.example.graph_with_bluetooth

import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*

@Composable
fun RSSIGraphScreen(deviceName: String, rssiData: List<Int>, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "RSSI Graph for $deviceName",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        AndroidView(
            factory = { context ->
                LineChart(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        600
                    )
                    description = Description().apply {
                        text = "RSSI over Time"
                    }
                }
            },
            update = { chart ->
                val entries = rssiData.mapIndexed { index, rssi ->
                    Entry(index.toFloat(), rssi.toFloat())
                }
                val dataSet = LineDataSet(entries, "RSSI").apply {
                    valueTextColor = android.graphics.Color.BLACK
                    valueTextSize = 12f
                }
                chart.data = LineData(dataSet)
                chart.invalidate()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text(text = "Back")
        }
    }
}
