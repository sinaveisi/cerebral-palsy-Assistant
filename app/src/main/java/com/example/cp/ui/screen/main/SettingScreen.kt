package com.example.cp.ui.screen.main

import com.example.cp.data.local.model.SettingItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cp.ui.screen.components.SettingCard
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.cp.R
import com.example.cp.navigation.ScreensRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController) {
    // Define the 6 items for your settings screen
    val settingItems = listOf(
        SettingItem(
            "یادآور",
            "دارو",
            Icons.Default.Notifications,
            Color(0xFFE9E7FD),
            ScreensRoute.MedicineReminder.route
        ),
        SettingItem(
            "یادآور",
            "پزشک",
            Icons.Default.Notifications,
            Color(0xFFE5F8E0),
            ScreensRoute.VisitReminder.route
        ),
        SettingItem(
            "یادآور",
            "توانبخشی",
            Icons.Default.Notifications,
            Color(0xFFFFF8D6),
            ScreensRoute.RecoveryReminder.route
        ),
        SettingItem(
            "محاسبه گر",
            "BMI",
            Icons.Default.Person,
            Color(0xFFF8E8E8),
            ScreensRoute.BMICalculator.route
        ),
        SettingItem(
            "دفترچه",
            "یادداشت",
            Icons.Default.Create,
            Color(0xFFE9E7FD),
            ScreensRoute.NoteList.route
        ),
        SettingItem(
            "درباره",
            "برنامه",
            Icons.Default.Info,
            Color(0xFFE5F8E0),
            ScreensRoute.AboutApp.route
        ),
//        SettingItem("نمایش", "Display", "Auto", "Bright", Icons.Default.Notifications, Color(0xFFE5F8E0)),
    )

    // The main layout is now a Column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 1. Call the new DonateCard at the top
        DonateCard(
            amount = "تنظیمات",
            modifier = Modifier.padding(16.dp)
        )

        // 2. The grid of setting items is placed below
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(settingItems) { item ->
                SettingCard(item = item) {
                    navController.navigate(item.route)
                }
            }
        }
    }
}

/**
 * A new composable for the header card, designed to match your image.
 */
@Composable
fun DonateCard(
    amount: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D474F)) // Dark Teal
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp)
        ) {
            // Faint background icon
            Icon(
                painter = painterResource(id = R.drawable.ic_setting_unfill),
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.08f),
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.TopEnd)
            )

            Column {
                Text(
                    text = "آماده خدمت رسانی",
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = amount,
                    color = Color.White,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                /*Button(
                    onClick = { *//* TODO: Handle Top up click *//* },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD2FF49), // Light Green
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Top up", fontWeight = FontWeight.Bold)
                }*/
            }

            // Location text at the bottom end
            /*Row(
                modifier = Modifier.align(Alignment.BottomEnd),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = location,
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }*/
        }
    }
}

