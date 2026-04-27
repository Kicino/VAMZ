package com.example.watertracker.dataRoom

import kotlinx.coroutines.flow.Flow

//robene podla https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#0
//funkcie ktore nam volaju SQL queries

interface ItemsRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Item>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<Item?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: Item)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: Item)

    suspend fun deleteAllItems()

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: Item)

    fun getTotalWaterStream(): Flow<Int?>
}