package com.example.cp.util

import android.app.AlarmManager
import android.app.PendingIntent // <-- FIXED TYPO
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.cp.data.local.entity.RecoveryReminderEntity
import com.example.cp.data.local.entity.ReminderEntity
import com.example.cp.data.local.entity.VisitReminderEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AlarmScheduler(private val context: Context) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(reminder: ReminderEntity) {
        val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
            putExtra("medicationName", reminder.medicationName)
            putExtra("dosage", reminder.dosage)
            putExtra("reminderId", reminder.id)
            putExtra("hour", reminder.hour)
            putExtra("minute", reminder.minute)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, reminder.hour)
            set(Calendar.MINUTE, reminder.minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            Log.d("AlarmScheduler", "SUCCESS: Alarm scheduled for reminder ID ${reminder.id} at: ${sdf.format(calendar.time)}")

        } catch (e: SecurityException) {
            Log.e("AlarmScheduler", "Could not schedule alarm. Permission may be missing.", e)
        }
    }

    fun cancel(reminder: ReminderEntity) {
        val intent = Intent(context, ReminderBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    fun scheduleVisit(visit: VisitReminderEntity) {
        val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
            // Use a different action to distinguish between reminder types
            action = "VISIT_REMINDER_ACTION"
            putExtra("doctorName", visit.doctorName)
            putExtra("specialty", visit.specialty)
            putExtra("reminderId", visit.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            visit.id, // Use the visit's ID
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Calculate the notification time (24 hours before the visit)
        val notificationTime = visit.visitDateTimeMillis - AlarmManager.INTERVAL_DAY

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                notificationTime,
                pendingIntent
            )
            Log.d("AlarmScheduler", "Visit alarm scheduled for ID ${visit.id}")
        } catch (e: SecurityException) {
            Log.e("AlarmScheduler", "Could not schedule visit alarm. Permission missing?", e)
        }
    }

    fun cancelVisit(visit: VisitReminderEntity) {
        val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
            action = "VISIT_REMINDER_ACTION"
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            visit.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    fun scheduleRecovery(recovery: RecoveryReminderEntity) {
        val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
            // Use a unique action to identify this type of reminder
            action = "RECOVERY_REMINDER_ACTION"
            putExtra("activityName", recovery.activityName)
            putExtra("duration", recovery.durationMinutes)
            putExtra("reminderId", recovery.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            recovery.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // The notification will fire at the exact time of the activity
        val notificationTime = recovery.reminderDateTimeMillis

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                notificationTime,
                pendingIntent
            )
            Log.d("AlarmScheduler", "Recovery alarm scheduled for ID ${recovery.id}")
        } catch (e: SecurityException) {
            Log.e("AlarmScheduler", "Could not schedule recovery alarm. Permission missing?", e)
        }
    }

    fun cancelRecovery(recovery: RecoveryReminderEntity) {
        val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
            action = "RECOVERY_REMINDER_ACTION"
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            recovery.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}
