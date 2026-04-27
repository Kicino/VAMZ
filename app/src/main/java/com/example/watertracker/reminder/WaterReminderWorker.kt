package com.example.watertracker.reminder

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.watertracker.R

class WaterReminderWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        val notification = NotificationCompat.Builder(applicationContext, "water_channel")
            .setContentTitle(applicationContext.getString(R.string.reminder_title))
            .setContentText(applicationContext.getString(R.string.reminder_text))
            .setSmallIcon(R.drawable.thirsty)
            .build()

        //kontrola ci mame opravnenue posielat notifikacie
        if (androidx.core.content.ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(applicationContext)
                .notify(System.currentTimeMillis().toInt(), notification)
        }

        return Result.success()
    }
}