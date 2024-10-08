package com.example.graph_with_bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val viewModel: HeartRateViewModel = viewModel()

                HeartRateApp(viewModel)
            }
        }
    }
}

@Composable
fun HeartRateApp(viewModel: HeartRateViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Simulated Heart Rate", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Start/Stop buttons to control data collection
        var isCollecting by remember { mutableStateOf(false) }
        Row {
            Button(
                onClick = {
                    if (!isCollecting) {
                        viewModel.startCollectingHeartRate()
                        isCollecting = true
                    }
                },
                enabled = !isCollecting
            ) {
                Text(text = "Start Collecting")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (isCollecting) {
                        viewModel.stopCollectingHeartRate()
                        isCollecting = false
                    }
                },
                enabled = isCollecting
            ) {
                Text(text = "Stop Collecting")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Heart rate graph
        HeartRateGraph(viewModel)
    }
}