package com.example.cp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DescriptionEntity(
    @PrimaryKey(autoGenerate = true)
    val descriptionId: Int = 0,
    val parentOwnerId: Int = 0,
    val title: String,
    val description: String
)