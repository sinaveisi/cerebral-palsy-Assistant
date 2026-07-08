package com.example.cp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cp.R

// Sealed class to define the items in our bottom navigation bar
sealed class BottomNavItem(
    val route: String,
    val icon: Int,
    val unselectedIcon: Int,
) {
    object Home : BottomNavItem(
        route = "home",
        icon = R.drawable.ic_home,
        unselectedIcon = R.drawable.ic_home_unfill,

    )

    object Profile : BottomNavItem(
        route = "profile",
        icon = R.drawable.ic_profile,
        unselectedIcon = R.drawable.ic_profile_unfill,
    )

    object Setting : BottomNavItem(
        route = "setting",
        icon = R.drawable.ic_setting,
        unselectedIcon = R.drawable.ic_setting_unfill,
    )
}