package com.example.watertracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.watertracker.API.WaterViewModel
import com.example.watertracker.data.ItemsRepository

class WaterViewModelFactory(
    private val repository: ItemsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WaterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}