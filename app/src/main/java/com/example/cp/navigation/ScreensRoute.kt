package com.example.cp.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class ScreensRoute(val route: String) {
    // ADD THIS for the screen that holds the bottom bar
    object Main : ScreensRoute("main_screen_layout")

    object SubTitle : ScreensRoute("subtitle_screen/{categoryId}/{categoryName}") {
        fun createRoute(categoryId: Int, categoryName: String): String {
            val encodedName = URLEncoder.encode(categoryName, StandardCharsets.UTF_8.name())
            return "subtitle_screen/$categoryId/$encodedName"
        }
    }

    // ADD THIS: A route for the final description screen
    object Description : ScreensRoute("description_screen/{titleId}") {
        fun createRoute(titleId: Int): String {
            return "description_screen/$titleId"
        }
    }

    // Add objects for each new settings screen
    object MedicineReminder : ScreensRoute("medicine_reminder_screen")
    object SetMedicineReminder : ScreensRoute("set_medicine_reminder_screen")

    object VisitReminder : ScreensRoute("visit_reminder_screen")
    object SetVisitReminder : ScreensRoute("set_visit_reminder_screen")

    object RecoveryReminder : ScreensRoute("recovery_reminder_screen")
    object SetRecoveryReminder : ScreensRoute("set_recovery_reminder_screen")

    object BMICalculator : ScreensRoute("bmi_calculator")
    object AboutBMI : ScreensRoute("bmi_about")

//    object DisplaySettings : ScreensRoute("display_settings")

    object NoteList : ScreensRoute("note_list_screen")
    object AboutApp : ScreensRoute("about_app_screen")
    object NoteEditor : ScreensRoute("note_editor_screen/{noteId}") {
        // noteId = -1 for a new note, or a real ID for an existing one
        fun createRoute(noteId: Int): String {
            return "note_editor_screen/$noteId"
        }
    }


    // ADD THESE: Routes for the profile feature
    object ProfileSelection : ScreensRoute("profile_selection_screen")
    object ParentProfileEditor : ScreensRoute("parent_profile_editor")
    object ChildProfileEditor : ScreensRoute("child_profile_editor")
}