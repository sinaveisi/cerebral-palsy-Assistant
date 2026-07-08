package com.example.cp.ui.screen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cp.navigation.BottomNavHost
import com.example.cp.ui.screen.components.AppBottomBar

@Composable
fun MainScreen(navController: NavController) { // It receives the top-level controller
    val bottomBarNavController = rememberNavController() // Creates a new one just for the bottom bar

    Scaffold(
        bottomBar = { AppBottomBar(navController = bottomBarNavController) }
    ) { innerPadding ->
        // Call the nested NavHost
        BottomNavHost(
            mainNavController = navController,
            bottomBarNavController = bottomBarNavController,
            innerPadding = innerPadding
        )
    }
}