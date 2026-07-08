package com.example.cp.ui.screen.setting.medicine

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cp.R
import com.example.cp.data.local.entity.ReminderEntity
import com.example.cp.navigation.ScreensRoute
import com.example.cp.ui.screen.components.ReminderCard
import com.example.cp.ui.viewmodel.EducateViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineReminderListScreen(
    navController: NavController,
    viewModel: EducateViewModel = hiltViewModel()
) {
    val reminders by viewModel.allReminders.collectAsState(initial = emptyList())
    val groupedReminders = reminders.groupBy { formatTime(it.hour, it.minute) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "لیست یادآور دارو",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(ScreensRoute.SetMedicineReminder.route) },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Reminder")
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            var globalCardIndex = 0
            groupedReminders.forEach { (time, remindersForTime) ->
                item {
                    TimeLineItem(
                        time = time,
                        reminders = remindersForTime,
                        viewModel = viewModel,
                        startIndex = globalCardIndex
                    )
                    globalCardIndex += remindersForTime.size
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}


@Composable
fun TimeLineItem(time: String, reminders: List<ReminderEntity>, viewModel: EducateViewModel, startIndex: Int) {
    // THE FIX: The list of colors is defined here.
    val cardColors = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)
    )
    val timelineColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.labelLarge.copy(
                lineHeight = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.width(56.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(1f)
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    drawLine(
                        color = timelineColor,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = 0f, y = size.height),
                        strokeWidth = strokeWidth
                    )
                }
                .padding(start = 16.dp)
        ) {
            reminders.forEachIndexed { localIndex, reminder ->
                // THE FIX: The correct color is passed to the card here.
                ReminderCard(
                    reminder = reminder,
                    containerColor = cardColors[(startIndex + localIndex) % cardColors.size],
                    onDeleteClick = {
                        viewModel.deleteReminder(reminder)
                    }
                )
            }
        }
    }
}


private fun formatTime(hour: Int, minute: Int): String {
    return String.format(Locale.US, "%02d:%02d", hour, minute)
}