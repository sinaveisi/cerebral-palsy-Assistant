package com.example.cp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cp.ui.screen.points.DescriptionScreen
import com.example.cp.ui.screen.MainScreen
import com.example.cp.ui.screen.points.SubTitleScreen
import com.example.cp.ui.screen.profile.ChildProfileEditorScreen
import com.example.cp.ui.screen.profile.ParentProfileEditorScreen
import com.example.cp.ui.screen.setting.aboutus.AboutUsScreen
import com.example.cp.ui.screen.setting.bmi.AboutBMIScreen
import com.example.cp.ui.screen.setting.bmi.BMICalculatorScreen
import com.example.cp.ui.screen.setting.medicine.MedicineReminderListScreen
import com.example.cp.ui.screen.setting.medicine.SetMedicineReminderScreen
import com.example.cp.ui.screen.setting.note.NoteEditorScreen
import com.example.cp.ui.screen.setting.note.NoteListScreen
import com.example.cp.ui.screen.setting.recovery.RecoveryReminderListScreen
import com.example.cp.ui.screen.setting.recovery.SetRecoveryReminderScreen
import com.example.cp.ui.screen.setting.visit.SetVisitReminderScreen
import com.example.cp.ui.screen.setting.visit.VisitReminderListScreen
import com.example.cp.ui.viewmodel.EducateViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavigation(viewModel: EducateViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreensRoute.Main.route) {
        // Route 1: Your MainScreen which contains the bottom bar
        composable(route = ScreensRoute.Main.route) {
            MainScreen(navController = navController)
        }

        // Route 2: The SubTitleScreen which will NOT have a bottom bar
        composable(
            route = ScreensRoute.SubTitle.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType },
                navArgument("categoryName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
            val encodedCategoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            val categoryName = URLDecoder.decode(encodedCategoryName, StandardCharsets.UTF_8.name())

            SubTitleScreen(
                navController = navController,
                categoryId = categoryId,
                categoryName = categoryName,
                viewModel = viewModel
            )
        }

        // ADD THIS: The destination for the new DescriptionScreen
        composable(
            route = ScreensRoute.Description.route,
            arguments = listOf(
                navArgument("titleId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val titleId = backStackEntry.arguments?.getInt("titleId") ?: 0
            DescriptionScreen(
                navController = navController,
                titleId = titleId,
                viewModel = viewModel
            )
        }

        // ADD THIS DESTINATION
        composable(route = ScreensRoute.MedicineReminder.route) {
            MedicineReminderListScreen(navController = navController, viewModel = viewModel)
        }

        // We will also need the route for setting a new reminder
        composable(route = ScreensRoute.SetMedicineReminder.route) {
            SetMedicineReminderScreen(navController = navController, viewModel = viewModel)
        }

        //Visit
        composable(route = ScreensRoute.VisitReminder.route) {
            VisitReminderListScreen(
                navController,
                viewModel
            )
        }
        composable(route = ScreensRoute.SetVisitReminder.route) {
            SetVisitReminderScreen(
                navController,
                viewModel
            )
        }

        //
        composable(route = ScreensRoute.RecoveryReminder.route) {
            RecoveryReminderListScreen(
                navController,
                viewModel
            )
        }
        composable(route = ScreensRoute.SetRecoveryReminder.route) {
            SetRecoveryReminderScreen(
                navController,
                viewModel
            )
        }


        composable(route = ScreensRoute.BMICalculator.route) { BMICalculatorScreen(navController) }
        composable(route = ScreensRoute.AboutBMI.route) { AboutBMIScreen(navController) }
        composable(route = ScreensRoute.AboutApp.route) { AboutUsScreen(navController) }


        // ADD THESE:
        composable(route = ScreensRoute.NoteList.route) {
            NoteListScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = ScreensRoute.NoteEditor.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
            NoteEditorScreen(navController = navController, noteId = noteId, viewModel = viewModel)
        }

        // ADD THESE:
        composable(route = ScreensRoute.ParentProfileEditor.route) {
            ParentProfileEditorScreen(navController = navController)
        }
        composable(route = ScreensRoute.ChildProfileEditor.route) {
            ChildProfileEditorScreen(navController = navController)
        }
    }
}
