package com.example.watertracker.dataRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Int,
    val timestamp: Long
)