package com.example.watertracker.reminder

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

fun scheduleReminders(context: Context, times: List<Pair<Int, Int>>) {

    val workManager = WorkManager.getInstance(context)

    workManager.cancelAllWork()

    times.forEach { (hour, minute) ->

        val now = Calendar.getInstance()
        ////Ai generovane vypocitanie planovania notifikacii na kokretny den
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)

            if (before(now)) add(Calendar.DAY_OF_YEAR, 1)
        }

        val delay = target.timeInMillis - now.timeInMillis

        val work = PeriodicWorkRequestBuilder<WaterReminderWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueue(work)
        ////
    }
}