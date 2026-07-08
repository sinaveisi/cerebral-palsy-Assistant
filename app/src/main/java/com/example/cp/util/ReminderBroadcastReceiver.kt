package com.example.cp.util

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.cp.NOTIFICATION_CHANNEL_ID
import com.example.cp.R
import com.example.cp.data.local.entity.ReminderEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// You need to add @AndroidEntryPoint to allow injecting the scheduler
@AndroidEntryPoint
class ReminderBroadcastReceiver : BroadcastReceiver() {

    // Inject the scheduler so we can use it here
    @Inject
    lateinit var scheduler: AlarmScheduler

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "VISIT_REMINDER_ACTION" -> handleVisitReminder(context, intent)
            "RECOVERY_REMINDER_ACTION" -> handleRecoveryReminder(
                context,
                intent
            ) // <-- ADD THIS CASE
            else -> handleMedicineReminder(context, intent)
        }
    }


    private fun handleMedicineReminder(context: Context, intent: Intent) {
        Log.d("ReminderReceiver", "Medicine alarm received!")
        // Add logging to see if the alarm is being received
        Log.d("ReminderReceiver", "Alarm received!")

        val medicationName = intent.getStringExtra("medicationName") ?: "Medication"
        val dosage = intent.getStringExtra("dosage") ?: ""
        val reminderId = intent.getIntExtra("reminderId", 0)
        val hour = intent.getIntExtra("hour", 0) // We need to pass these from the scheduler
        val minute = intent.getIntExtra("minute", 0)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("یادآور دارو")
            .setContentText("وقت مصرف داروی $medicationName ($dosage) است.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(reminderId, notification)

        // --- THE FIX FOR REPEATING ---
        // Create a representation of the fired reminder and reschedule it for the next day.
        val firedReminder = ReminderEntity(
            id = reminderId,
            hour = hour,
            minute = minute,
            medicationName = medicationName,
            dosage = dosage,
            type = "" // Type isn't needed for rescheduling
        )
        scheduler.schedule(firedReminder)
        Log.d("ReminderReceiver", "Rescheduled alarm for reminder ID $reminderId for the next day.")
    }

    private fun handleVisitReminder(context: Context, intent: Intent) {
        Log.d("ReminderReceiver", "Visit alarm received!")
        val doctorName = intent.getStringExtra("doctorName") ?: "Doctor"
        val specialty = intent.getStringExtra("specialty") ?: ""
        val reminderId = intent.getIntExtra("reminderId", 0)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("یادآور ویزیت")
            .setContentText("فردا با دکتر $doctorName ($specialty) قرار ملاقات دارید.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(reminderId, notification)
    }

    private fun handleRecoveryReminder(context: Context, intent: Intent) {
        Log.d("ReminderReceiver", "Recovery alarm received!")
        val activityName = intent.getStringExtra("activityName") ?: "Recovery Activity"
        val duration = intent.getIntExtra("duration", 0)
        val reminderId = intent.getIntExtra("reminderId", 0)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("یادآور تمرین")
            .setContentText("وقت انجام $activityName به مدت $duration دقیقه است.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(reminderId, notification)
    }
}