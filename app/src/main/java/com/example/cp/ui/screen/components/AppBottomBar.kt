package com.example.cp.ui.screen.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cp.navigation.BottomNavItem
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource

@Composable
fun AppBottomBar(navController: NavHostController) {
    val navItems = listOf(
        BottomNavItem.Profile,
        BottomNavItem.Home,
        BottomNavItem.Setting
    )

    // A Surface container for elevation and theming
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp, // Add a subtle shadow
        color = MaterialTheme.colorScheme.surface, // Use theme color
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .height(64.dp) // A standard height for bottom bars
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            navItems.forEach { item ->
                ModernBottomBarItem(
                    item = item,
                    isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun ModernBottomBarItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Animate the background color for the pill shape
    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent,
        animationSpec = tween(durationMillis = 300)
    )

    // Animate the content color for the icon
    val animatedContentColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        animationSpec = tween(durationMillis = 300)
    )

    // Animate the scale of the item
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1.0f,
        animationSpec = tween(durationMillis = 300),
        label = "scale"
    )

    // The interactive item container
    Box(
        modifier = Modifier
            .scale(animatedScale) // Apply the scale animation
            .clip(CircleShape) // A circular clip for the background
            .background(animatedBackgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        // Crossfade smoothly animates between the two icons
        Crossfade(
            targetState = isSelected,
            animationSpec = tween(durationMillis = 300),
            label = "icon_crossfade"
        ) { isItemSelected ->
            Image(
                painter = painterResource(if (isItemSelected) item.icon else item.unselectedIcon),
                contentDescription = item.route,
                modifier = Modifier.padding(12.dp) // Add some padding around the icon
            )
        }
    }
}