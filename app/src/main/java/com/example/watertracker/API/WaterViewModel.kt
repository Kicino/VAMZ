package com.example.watertracker.API

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watertracker.data.Item
import com.example.watertracker.data.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WaterViewModel(
    private val repository: ItemsRepository
) : ViewModel() {

    //celkové množstvo vody
    val totalWater: StateFlow<Int> =
        repository.getTotalWaterStream()
            .map { it ?: 0 }
            .stateIn(
                viewModelScope,
                SharingStarted.Companion.WhileSubscribed(5000),
                0
            )

    //pridanie vody
    fun addWater(amount: Int) {
        viewModelScope.launch {
            repository.insertItem(
                Item(
                    amount = amount,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }
}