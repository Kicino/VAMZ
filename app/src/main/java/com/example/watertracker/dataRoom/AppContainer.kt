package com.example.watertracker.dataRoom

import android.content.Context
import com.example.watertracker.dataStore.UserPreferencesRepository

interface AppContainer {
    val itemsRepository: ItemsRepository
    val userPreferencesRepository: UserPreferencesRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context)
    }
}
