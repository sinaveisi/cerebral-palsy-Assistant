package com.example.cp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cp.data.local.dao.EducateDAO
import com.example.cp.data.local.entity.ChildProfileEntity
import com.example.cp.data.local.entity.DescriptionEntity
import com.example.cp.data.local.entity.NoteEntity
import com.example.cp.data.local.entity.ParentProfileEntity
import com.example.cp.data.local.entity.RecoveryReminderEntity
import com.example.cp.data.local.entity.ReminderEntity
import com.example.cp.data.local.entity.TitleEntity
import com.example.cp.data.local.entity.VisitReminderEntity

@Database(
    entities = [
        TitleEntity::class,
        DescriptionEntity::class,
        ReminderEntity::class,
        VisitReminderEntity::class,
        RecoveryReminderEntity::class,
        NoteEntity::class,
        ParentProfileEntity::class,
        ChildProfileEntity::class
    ],
    version = 1,
    exportSchema = false // Recommended to disable for simplicity
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun educateDAO(): EducateDAO
}