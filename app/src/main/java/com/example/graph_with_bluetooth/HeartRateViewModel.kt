package com.example.graph_with_bluetooth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class HeartRateViewModel : ViewModel() {

    // Simulated list of heart rate data (in BPM)
    private val _heartRateData = MutableStateFlow<List<Int>>(emptyList())
    val heartRateData: StateFlow<List<Int>> = _heartRateData

    private var isCollecting = false
    private var collectionJob: Job? = null

    // Simulate collecting heart rate data
    fun startCollectingHeartRate() {
        if (!isCollecting) {
            isCollecting = true
            collectionJob = CoroutineScope(Dispatchers.IO).launch {
                while (isCollecting) {
                    // Simulate a heart rate value between 60 and 100 BPM
                    val newHeartRate = Random.nextInt(60, 101)
                    val updatedList = _heartRateData.value.toMutableList()
                    updatedList.add(newHeartRate)
                    _heartRateData.value = updatedList

                    // Simulate the delay between heart rate updates
                    delay(1000L) // 1-second interval
                }
            }
        }
    }

    fun stopCollectingHeartRate() {
        isCollecting = false
        collectionJob?.cancel()
    }
}