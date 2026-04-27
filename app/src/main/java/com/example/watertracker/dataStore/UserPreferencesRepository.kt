package com.example.watertracker.dataStore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")
private val REMINDER_TIMES = stringPreferencesKey("reminder_times")

class UserPreferencesRepository(private val context: Context) {

    //ulozenie denneho golu
    companion object {
        val DAILY_GOAL = intPreferencesKey("daily_goal")
    }

    //getter na daily goal, ak je prazdny, da nam 2500
    val dailyGoalFlow = context.dataStore.data
        .map { preferences ->
            preferences[DAILY_GOAL] ?: 2500
        }

    //setter na daily Goal
    suspend fun saveDailyGoal(goal: Int) {
        context.dataStore.edit { preferences ->
            preferences[DAILY_GOAL] = goal
        }
    }


    ///////ulozenie casov na pripomienky do pola
    ////AI generovane
    suspend fun saveReminderTimes(times: List<Pair<Int, Int>>) {
        val serialized = times.joinToString(",") { "${it.first}:${it.second}" }

        context.dataStore.edit { prefs ->
            prefs[REMINDER_TIMES] = serialized
        }
    }
    ////

    //nacitanie casov na pripomienky
    ////AI generovane
    val reminderTimesFlow = context.dataStore.data.map { prefs ->
        prefs[REMINDER_TIMES]
            ?.takeIf { it.isNotBlank() }
            ?.split(",")
            ?.mapNotNull {
                val parts = it.split(":")
                if (parts.size == 2) {
                    val h = parts[0].toIntOrNull()
                    val m = parts[1].toIntOrNull()
                    if (h != null && m != null) Pair(h, m) else null
                } else null
            }
            ?: emptyList()
    }
    ////
}