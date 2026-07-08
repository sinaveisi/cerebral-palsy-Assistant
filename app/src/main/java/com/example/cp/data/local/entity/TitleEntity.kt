package com.example.cp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TitleEntity(
    @PrimaryKey(autoGenerate = true)
    val titleId: Int = 0,
    val parentId: Int,
    val title: String
)