package com.example.watertracker.dataRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

//robene podla https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#0
@Entity(tableName = "water")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Int,
    val timestamp: Long
)