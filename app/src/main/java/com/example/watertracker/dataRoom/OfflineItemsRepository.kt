package com.example.watertracker.dataRoom

import kotlinx.coroutines.flow.Flow

//robene podla https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#0
//funkcie ktore nam volaju SQL queries ked sme offline
class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)

    override suspend fun insertItem(item: Item) = itemDao.insert(item)

    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    override suspend fun updateItem(item: Item) = itemDao.update(item)

    override fun getTotalWaterStream(): Flow<Int?> {
        return itemDao.getTotalWater()
    }

    override suspend fun deleteAllItems() {
        itemDao.deleteAll()
    }
}