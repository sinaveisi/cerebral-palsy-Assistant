package com.example.cp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cp.ui.screen.main.HomeScreen
import com.example.cp.ui.screen.main.SettingScreen
import com.example.cp.ui.viewmodel.EducateViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cp.ui.screen.main.ProfileSelectionScreen

@Composable
fun BottomNavHost(
    mainNavController: NavController, // The top-level controller
    bottomBarNavController: NavHostController, // The controller for these tabs
    innerPadding: PaddingValues
) {
    NavHost(
        navController = bottomBarNavController,
        startDestination = BottomNavItem.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = BottomNavItem.Home.route) {
            // Give HomeScreen the MAIN NavController so it can navigate to SubTitleScreen
            val viewModel: EducateViewModel = hiltViewModel()
            HomeScreen(navController = mainNavController)
        }
        composable(route = BottomNavItem.Profile.route) {
            ProfileSelectionScreen(navController = mainNavController)
        }
        composable(route = BottomNavItem.Setting.route) {
            SettingScreen(navController = mainNavController)
        }
    }
}
