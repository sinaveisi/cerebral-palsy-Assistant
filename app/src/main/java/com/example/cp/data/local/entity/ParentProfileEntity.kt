package com.example.cp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parent_profile_table")
data class ParentProfileEntity(
    @PrimaryKey val id: Int = 1, // Fixed ID for the single profile
    val age: String = "",
    val educationLevel: String = "",
    val jobStatus: String = "", // e.g., "Full-time", "Part-time", "Homemaker"
    val maritalStatus: String = "", // e.g., "With spouse", "Without spouse"
    val monthlyIncome: String = "",
    val residence: String = "",
    val contactNumber: String = "",
    val familyHistory: String = "", // e.g., "Yes", "No"
    val careHoursPerDay: String = ""
)