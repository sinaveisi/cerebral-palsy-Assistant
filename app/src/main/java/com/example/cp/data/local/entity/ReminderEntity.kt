package com.example.cp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder_table")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hour: Int,
    val minute: Int,
    val medicationName: String,
    val dosage: String,
    val type: String // e.g., "capsule", "tablet", "drops"
)