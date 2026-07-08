package com.example.cp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "child_profile_table")
data class ChildProfileEntity(
    @PrimaryKey val id: Int = 1, // Fixed ID for the single profile
    val gender: String = "",
    val age: String = "",
    val weight: String = "",
    val height: String = "",
    val birthOrder: String = "", // e.g., "First", "Second"
    val insuranceType: String = "",
    val diagnosisDate: String = "",
    val cpType: String = "",
    val gmfcsLevel: String = "",
    val primarySymptoms: String = "",
    val secondarySymptoms: String = "",
    val medications: String = "",
    val treatmentsUsed: String = "",
    val surgicalHistory: String = "",
    val otherConditions: String = ""
)