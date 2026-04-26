package com.example.watertracker.dataStore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        val DAILY_GOAL = intPreferencesKey("daily_goal")
    }

    val dailyGoalFlow = context.dataStore.data
        .map { preferences ->
            preferences[DAILY_GOAL] ?: 2500
        }

    suspend fun saveDailyGoal(goal: Int) {
        context.dataStore.edit { preferences ->
            preferences[DAILY_GOAL] = goal
        }
    }
}