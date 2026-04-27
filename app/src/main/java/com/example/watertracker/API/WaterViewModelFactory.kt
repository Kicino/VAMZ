package com.example.watertracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.watertracker.API.WaterViewModel
import com.example.watertracker.dataRoom.ItemsRepository
import com.example.watertracker.dataStore.UserPreferencesRepository


////Ai generovany viewModel s datami na ich presuvanie po displejoch a pouzivanie
class WaterViewModelFactory(
    private val itemsRepository: ItemsRepository,
    private val preferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WaterViewModel(
            itemsRepository,
            preferencesRepository
        ) as T
    }
}