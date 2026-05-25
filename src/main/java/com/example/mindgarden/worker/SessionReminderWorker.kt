package com.example.mindgarden.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

/**
 * WorkManager-воркер для ежедневного напоминания о фокус-сессии.
 * Запускается один раз в день в заданное время.
 */
@HiltWorker
class SessionReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "daily_session_reminder"
        const val CHANNEL_ID = "mindgarden_reminders"
        const val CHANNEL_NAME = "Напоминания о сессии"
        private const val NOTIFICATION_ID = 1001

        /**
         * Планирует ежедневное напоминание через WorkManager.
         * @param context контекст приложения
         * @param delayHours задержка до первого запуска (часы)
         */
        fun schedule(context: Context, delayHours: Long = 20) {
            val request = PeriodicWorkRequestBuilder<SessionReminderWorker>(
                repeatInterval = 24,
                repeatIntervalTimeUnit = TimeUnit.HOURS
            )
                .setInitialDelay(delayHours, TimeUnit.HOURS)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiresBatteryNotLow(true)
                        .build()
                )
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                request
            )
        }
    }

    override suspend fun doWork(): Result {
        createNotificationChannel()
        showNotification()
        return Result.success()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Ежедневные напоминания о фокус-сессии"
            }
            val manager = applicationContext.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun showNotification() {
        val messages = listOf(
            "Пора вырастить цветок! Начни фокус-сессию сегодня 🌱",
            "Твой сад ждёт пополнения. Уделишь 25 минут? 🌸",
            "Маленькие шаги делают большой сад. Начнём? 🌺",
            "Ты не занимался сегодня — самое время вырастить цветок! 🌻"
        )
        val message = messages.random()

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("MindGarden")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        val manager = applicationContext.getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, notification)
    }
}
