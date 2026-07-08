package com.example.cp.data.repository

import com.example.cp.data.local.dao.EducateDAO
import com.example.cp.data.local.entity.ChildProfileEntity
import com.example.cp.data.local.entity.DescriptionEntity
import com.example.cp.data.local.entity.NoteEntity
import com.example.cp.data.local.entity.ParentProfileEntity
import com.example.cp.data.local.entity.RecoveryReminderEntity
import com.example.cp.data.local.entity.ReminderEntity
import com.example.cp.data.local.entity.TitleEntity
import com.example.cp.data.local.entity.VisitReminderEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EducateRepository @Inject constructor(private val educateDAO: EducateDAO) {

    suspend fun insertTitle(titleEntity: TitleEntity) {
        educateDAO.insertTitle(titleEntity)
    }

    suspend fun insertDescription(descriptionEntity: DescriptionEntity) {
        educateDAO.insertDescription(descriptionEntity)
    }

    fun getTitleWithDescription(titleId: Int) = educateDAO.getTitleWithDescription(titleId)

    fun getAllParents() = educateDAO.getAllTitle()

    // ... (inside the EducateRepository class)

    fun getTitlesByParentId(parentId: Int) = educateDAO.getTitlesByParentId(parentId)

    // ... inside EducateRepository class
    suspend fun insertReminderAndGetId(reminder: ReminderEntity): Long {
        return educateDAO.insertReminder(reminder)
    }

    fun getAllReminders() = educateDAO.getAllReminders()

    suspend fun deleteReminder(reminder: ReminderEntity) {
        educateDAO.deleteReminder(reminder)
    }

    suspend fun insertVisitReminderAndGetId(visitReminder: VisitReminderEntity): Long {
        return educateDAO.insertVisitReminder(visitReminder)
    }

    fun getAllVisitReminders() = educateDAO.getAllVisitReminders()

    suspend fun deleteVisitReminder(visitReminder: VisitReminderEntity) {
        educateDAO.deleteVisitReminder(visitReminder)
    }


    suspend fun insertRecoveryReminderAndGetId(recoveryReminder: RecoveryReminderEntity): Long {
        return educateDAO.insertRecoveryReminder(recoveryReminder)
    }

    fun getAllRecoveryReminders() = educateDAO.getAllRecoveryReminders()

    suspend fun deleteRecoveryReminder(recoveryReminder: RecoveryReminderEntity) {
        educateDAO.deleteRecoveryReminder(recoveryReminder)
    }


    suspend fun insertNote(note: NoteEntity) = educateDAO.insertNote(note)
    suspend fun updateNote(note: NoteEntity) = educateDAO.updateNote(note)
    suspend fun deleteNote(note: NoteEntity) = educateDAO.deleteNote(note)
    fun getAllNotes() = educateDAO.getAllNotes()
    fun getNoteById(noteId: Int) = educateDAO.getNoteById(noteId)


    suspend fun upsertParentProfile(profile: ParentProfileEntity) = educateDAO.upsertParentProfile(profile)
    fun getParentProfile(): Flow<ParentProfileEntity?> = educateDAO.getParentProfile()

    suspend fun upsertChildProfile(profile: ChildProfileEntity) = educateDAO.upsertChildProfile(profile)
    fun getChildProfile(): Flow<ChildProfileEntity?> = educateDAO.getChildProfile()
}