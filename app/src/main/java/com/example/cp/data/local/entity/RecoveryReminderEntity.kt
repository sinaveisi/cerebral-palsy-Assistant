package com.example.cp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recovery_reminder_table")
data class RecoveryReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val activityName: String, // e.g., "Stretching", "Physical Therapy"
    val durationMinutes: Int, // e.g., 30
    val reminderDateTimeMillis: Long // Stores the exact date and time for the reminder
)