package com.example.cp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cp.data.local.entity.ChildProfileEntity
import com.example.cp.data.local.entity.DescriptionEntity
import com.example.cp.data.local.entity.NoteEntity
import com.example.cp.data.local.entity.ParentProfileEntity
import com.example.cp.data.local.entity.RecoveryReminderEntity
import com.example.cp.data.local.entity.ReminderEntity
import com.example.cp.data.local.entity.TitleEntity
import com.example.cp.data.local.entity.VisitReminderEntity
import com.example.cp.data.repository.EducateRepository
import com.example.cp.util.AlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EducateViewModel @Inject constructor(
    private val repository: EducateRepository,
    private val scheduler: AlarmScheduler
) : ViewModel() {

    /**
     * Gets a list of all titles belonging to a specific parent category.
     * This is called by the SubTitleScreen to populate its list of cards.
     * @param categoryId The ID of the main category selected on the HomeScreen.
     * @return A Flow that emits the list of titles for that category.
     */
    fun getTitlesForCategory(categoryId: Int) = repository.getTitlesByParentId(categoryId)

    /**
     * Gets a specific title along with all its associated descriptions.
     * This will be called by the final screen to display the detailed content.
     * @param titleId The ID of the title selected on the SubTitleScreen.
     * @return A Flow that emits the title and its list of descriptions.
     */
    fun getDetailsForTitle(titleId: Int) = repository.getTitleWithDescription(titleId)


    // The following functions are for completeness and are not currently called by the UI,
    // as the data is pre-populated. They could be used later if you add features
    // for users to add their own notes or content.

    /**
     * Inserts a new parent title into the database.
     */
    fun addTitle(titleEntity: TitleEntity) {
        viewModelScope.launch {
            repository.insertTitle(titleEntity)
        }
    }

    /**
     * Inserts a new child description, linking it to a parent title.
     */
    fun addDescription(descriptionEntity: DescriptionEntity) {
        viewModelScope.launch {
            repository.insertDescription(descriptionEntity)
        }
    }

    // ... inside EducateViewModel class
    val allReminders = repository.getAllReminders()

    // 2. UPDATE THIS FUNCTION
    fun addReminder(hour: Int, minute: Int, name: String, dosage: String) {
        viewModelScope.launch {
            val newReminder = ReminderEntity(
                hour = hour,
                minute = minute,
                medicationName = name,
                dosage = dosage,
                type = "capsule" // You can change this based on user input
            )
            // Get the ID of the newly inserted reminder
            val newId = repository.insertReminderAndGetId(newReminder)
            // Schedule the alarm with the correct ID
            scheduler.schedule(newReminder.copy(id = newId.toInt()))
        }
    }

    fun deleteReminder(reminder: ReminderEntity) {
        viewModelScope.launch {
            // First, cancel the scheduled alarm
            scheduler.cancel(reminder)
            // Then, delete the reminder from the database
            repository.deleteReminder(reminder)
        }
    }

    val allVisitReminders = repository.getAllVisitReminders()

    fun addVisitReminder(
        doctorName: String,
        specialty: String,
        location: String,
        dateTimeMillis: Long
    ) {
        viewModelScope.launch {
            val newVisit = VisitReminderEntity(
                doctorName = doctorName,
                specialty = specialty,
                location = location,
                visitDateTimeMillis = dateTimeMillis
            )
            val newId = repository.insertVisitReminderAndGetId(newVisit)
            // Schedule the alarm using the new entity
            scheduler.scheduleVisit(newVisit.copy(id = newId.toInt()))
        }
    }

    fun deleteVisitReminder(visitReminder: VisitReminderEntity) {
        viewModelScope.launch {
            scheduler.cancelVisit(visitReminder)
            repository.deleteVisitReminder(visitReminder)
        }
    }

    val allRecoveryReminders = repository.getAllRecoveryReminders()

    fun addRecoveryReminder(
        activityName: String,
        durationMinutes: Int,
        dateTimeMillis: Long
    ) {
        viewModelScope.launch {
            val newRecovery = RecoveryReminderEntity(
                activityName = activityName,
                durationMinutes = durationMinutes,
                reminderDateTimeMillis = dateTimeMillis
            )
            val newId = repository.insertRecoveryReminderAndGetId(newRecovery)
            scheduler.scheduleRecovery(newRecovery.copy(id = newId.toInt()))
        }
    }

    fun deleteRecoveryReminder(recoveryReminder: RecoveryReminderEntity) {
        viewModelScope.launch {
            scheduler.cancelRecovery(recoveryReminder)
            repository.deleteRecoveryReminder(recoveryReminder)
        }
    }


    val allNotes = repository.getAllNotes()

    fun getNoteById(noteId: Int) = repository.getNoteById(noteId)

    // Updated to use priority and colorHex
    fun upsertNote(
        noteId: Int,
        title: String,
        content: String,
        priority: String,
        colorHex: String,
        onNoteSaved: () -> Unit
    ) {
        viewModelScope.launch {
            val noteToSave = NoteEntity(
                id = if (noteId == -1) 0 else noteId,
                title = title,
                content = content,
                priority = priority,
                colorHex = colorHex
            )
            if (noteToSave.id == 0) {
                repository.insertNote(noteToSave)
            } else {
                repository.updateNote(noteToSave)
            }
            onNoteSaved()
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    // StateFlow for Parent Profile
    private val _parentProfile = MutableStateFlow<ParentProfileEntity?>(null)
    val parentProfile: StateFlow<ParentProfileEntity?> = _parentProfile.asStateFlow()

    // StateFlow for Child Profile
    private val _childProfile = MutableStateFlow<ChildProfileEntity?>(null)
    val childProfile: StateFlow<ChildProfileEntity?> = _childProfile.asStateFlow()

    init {
        // Load the profiles as soon as the ViewModel is created
        viewModelScope.launch {
            repository.getParentProfile().collect { _parentProfile.value = it }
        }
        viewModelScope.launch {
            repository.getChildProfile().collect { _childProfile.value = it }
        }
    }

    fun saveParentProfile(profile: ParentProfileEntity) {
        viewModelScope.launch {
            repository.upsertParentProfile(profile)
        }
    }

    fun saveChildProfile(profile: ChildProfileEntity) {
        viewModelScope.launch {
            repository.upsertChildProfile(profile)
        }
    }

}