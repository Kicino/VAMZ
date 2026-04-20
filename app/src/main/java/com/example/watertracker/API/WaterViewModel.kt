package com.example.watertracker.API

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watertracker.data.Item
import com.example.watertracker.data.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

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
    //resetnutie celej tabulky, pre testovanie
    fun resetData() {
        viewModelScope.launch {
            repository.deleteAllItems()
        }
    }

    //priprava dat na graf
    val weeklyData: StateFlow<List<Pair<Int, Int>>> =
        repository.getAllItemsStream()
            .map { items ->

                val now = System.currentTimeMillis()
                val dayMillis = 24 * 60 * 60 * 1000L

                (0..6).map { i ->
                    val start = now - i * dayMillis
                    val dayStart = start - (start % dayMillis)
                    val dayEnd = dayStart + dayMillis

                    val total = items
                        .filter { it.timestamp in dayStart until dayEnd }
                        .sumOf { it.amount }

                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = dayStart

                    val day = calendar.get(Calendar.DAY_OF_WEEK)

                    day to total
                }.reversed()
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    val allItems: StateFlow<List<Item>> =
        repository.getAllItemsStream()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}