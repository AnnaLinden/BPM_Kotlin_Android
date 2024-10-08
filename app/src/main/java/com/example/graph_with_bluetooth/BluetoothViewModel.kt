package com.example.graph_with_bluetooth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Device(
    val address: String,
    val name: String
)

class BluetoothViewModel : ViewModel() {

    private val _rssiData = mutableMapOf<String, MutableList<Int>>()
    private val _selectedDevice = MutableStateFlow<Device?>(null)
    val selectedDevice: StateFlow<Device?> = _selectedDevice

    fun addRSSI(address: String, rssi: Int) {
        val list = _rssiData.getOrPut(address) { mutableListOf() }
        list.add(rssi)
    }

    fun getRSSIData(address: String): List<Int> {
        return _rssiData[address]?.toList() ?: emptyList()
    }

    fun selectDevice(address: String, name: String) {
        _selectedDevice.value = Device(address, name)
    }

    fun clearSelection() {
        _selectedDevice.value = null
    }

    fun clearData() {
        _rssiData.clear()
    }
}
