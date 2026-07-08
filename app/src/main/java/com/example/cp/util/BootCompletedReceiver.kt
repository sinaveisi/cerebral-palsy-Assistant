package com.example.cp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.cp.data.repository.EducateRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: EducateRepository
    @Inject
    lateinit var scheduler: AlarmScheduler

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                // Re-schedule daily medicine reminders
                val medicineReminders = repository.getAllReminders().first()
                medicineReminders.forEach { reminder ->
                    scheduler.schedule(reminder)
                }

                // Re-schedule one-time visit reminders
                val visitReminders = repository.getAllVisitReminders().first()
                visitReminders.forEach { visit ->
                    scheduler.scheduleVisit(visit)
                }

                // Re-schedule one-time recovery reminders
                val recoveryReminders = repository.getAllRecoveryReminders().first()
                recoveryReminders.forEach { recovery ->
                    scheduler.scheduleRecovery(recovery)
                }
            }
        }
    }
}