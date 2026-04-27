package com.example.watertracker.API

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watertracker.dataRoom.Item
import com.example.watertracker.dataRoom.ItemsRepository
import com.example.watertracker.dataStore.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class WaterViewModel(
    private val repository: ItemsRepository,
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    //premenna na daily goal
    val dailyGoal: StateFlow<Int> =
        preferencesRepository.dailyGoalFlow
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                2500
            )

    //setter na daily goal
    fun setDailyGoal(goal: Int) {
        viewModelScope.launch {
            preferencesRepository.saveDailyGoal(goal)
        }
    }


    //celkové množstvo vody
//    val totalWater: StateFlow<Int> =
//        repository.getTotalWaterStream()
//            .map { it ?: 0 }
//            .stateIn(
//                viewModelScope,
//                SharingStarted.WhileSubscribed(5000),
//                0
//            )

    //len dnesna hodnota vody
    val todayWater: StateFlow<Int> =
        repository.getAllItemsStream()
            .map { items ->

                val cal = Calendar.getInstance()

                // začiatok dnešného dňa
                cal.set(Calendar.HOUR_OF_DAY, 0)
                cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0)
                cal.set(Calendar.MILLISECOND, 0)

                val start = cal.timeInMillis

                // koniec dňa
                cal.add(Calendar.DAY_OF_YEAR, 1)
                val end = cal.timeInMillis

                items
                    .filter { it.timestamp in start until end }
                    .sumOf { it.amount }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
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

                (0..6).map { i ->

                    val cal = Calendar.getInstance()
                    cal.add(Calendar.DAY_OF_YEAR, -i)

                    // začiatok dňa (00:00)
                    cal.set(Calendar.HOUR_OF_DAY, 0)
                    cal.set(Calendar.MINUTE, 0)
                    cal.set(Calendar.SECOND, 0)
                    cal.set(Calendar.MILLISECOND, 0)

                    val dayStart = cal.timeInMillis

                    // uložíme deň (SPRÁVNY)
                    val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)

                    // koniec dňa
                    cal.add(Calendar.DAY_OF_YEAR, 1)
                    val dayEnd = cal.timeInMillis

                    val total = items
                        .filter { it.timestamp in dayStart until dayEnd }
                        .sumOf { it.amount }

                    dayOfWeek to total
                }.reversed()
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    val allItems: StateFlow<List<Item>> =
        repository.getAllItemsStream()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    //pripomienky na pitie vody
    val reminderTimes: StateFlow<List<Pair<Int, Int>>> =
        preferencesRepository.reminderTimesFlow
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    //setter
    fun setReminderTimes(times: List<Pair<Int, Int>>) {
        viewModelScope.launch {
            preferencesRepository.saveReminderTimes(times)
        }
    }
}