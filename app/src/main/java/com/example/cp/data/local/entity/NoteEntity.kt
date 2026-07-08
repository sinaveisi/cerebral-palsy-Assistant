package com.example.cp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val priority: String, // Replaced 'tags' with 'priority' (e.g., "Normal", "Important")
    val colorHex: String, // The color will be determined by the priority
    val timestamp: Long = System.currentTimeMillis()
)