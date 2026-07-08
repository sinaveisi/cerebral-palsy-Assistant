package com.example.cp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visit_reminder_table")
data class VisitReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val doctorName: String,
    val specialty: String,
    val location: String,
    val visitDateTimeMillis: Long // Stores the exact date and time
)