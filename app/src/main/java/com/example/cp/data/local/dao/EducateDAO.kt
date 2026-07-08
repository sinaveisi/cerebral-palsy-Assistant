package com.example.cp.data.local.dao


import androidx.room.*
import com.example.cp.data.local.entity.ChildProfileEntity
import com.example.cp.data.local.entity.DescriptionEntity
import com.example.cp.data.local.entity.NoteEntity
import com.example.cp.data.local.entity.ParentProfileEntity
import com.example.cp.data.local.entity.RecoveryReminderEntity
import com.example.cp.data.local.entity.ReminderEntity
import com.example.cp.data.local.entity.TitleEntity
import com.example.cp.data.local.entity.TitleWithDescription
import com.example.cp.data.local.entity.VisitReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EducateDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTitle(titleEntity: TitleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDescription(descriptionEntity: DescriptionEntity)

    // Query to get a parent with all its children
    // Using Flow to get real-time updates in the UI
    @Transaction
    @Query("SELECT * FROM TitleEntity WHERE titleId = :titleId")
    fun getTitleWithDescription(titleId: Int): Flow<TitleWithDescription>

    // Query to get all parents (if you need a list of them)
    @Query("SELECT * FROM TitleEntity ORDER BY titleId ASC")
    fun getAllTitle(): Flow<List<TitleEntity>>

    // ... (inside the EducateDAO interface)

    @Transaction
    @Query("SELECT * FROM TitleEntity WHERE parentId = :parentId")
    fun getTitlesByParentId(parentId: Int): Flow<List<TitleEntity>>


    // ... inside EducateDAO interface
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity): Long

    @Query("SELECT * FROM reminder_table ORDER BY hour, minute ASC")
    fun getAllReminders(): Flow<List<ReminderEntity>>

    @Delete
    suspend fun deleteReminder(reminder: ReminderEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisitReminder(visitReminder: VisitReminderEntity): Long

    @Query("SELECT * FROM visit_reminder_table ORDER BY visitDateTimeMillis ASC")
    fun getAllVisitReminders(): Flow<List<VisitReminderEntity>>

    @Delete
    suspend fun deleteVisitReminder(visitReminder: VisitReminderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecoveryReminder(recoveryReminder: RecoveryReminderEntity): Long

    @Query("SELECT * FROM recovery_reminder_table ORDER BY reminderDateTimeMillis ASC")
    fun getAllRecoveryReminders(): Flow<List<RecoveryReminderEntity>>

    @Delete
    suspend fun deleteRecoveryReminder(recoveryReminder: RecoveryReminderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM notes_table ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table WHERE id = :noteId")
    fun getNoteById(noteId: Int): Flow<NoteEntity?>


    @Upsert
    suspend fun upsertParentProfile(profile: ParentProfileEntity)

    @Query("SELECT * FROM parent_profile_table WHERE id = 1")
    fun getParentProfile(): Flow<ParentProfileEntity?>

    @Upsert
    suspend fun upsertChildProfile(profile: ChildProfileEntity)

    @Query("SELECT * FROM child_profile_table WHERE id = 1")
    fun getChildProfile(): Flow<ChildProfileEntity?>
}