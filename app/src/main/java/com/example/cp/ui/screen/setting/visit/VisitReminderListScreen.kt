package com.example.cp.ui.screen.setting.visit

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cp.R
import com.example.cp.data.local.entity.VisitReminderEntity
import com.example.cp.navigation.ScreensRoute
import com.example.cp.ui.viewmodel.EducateViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitReminderListScreen(
    navController: NavController,
    viewModel: EducateViewModel
) {
    val reminders by viewModel.allVisitReminders.collectAsState(initial = emptyList())
    val groupedReminders = reminders.groupBy { formatDate(it.visitDateTimeMillis) }
    val timelineLineColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "لیست یادآور ویزیت",
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
                onClick = { navController.navigate(ScreensRoute.SetVisitReminder.route) },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Visit Reminder")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                var globalCardIndex = 0
                groupedReminders.forEach { (date, remindersForDate) ->
                    item {
                        VisitTimelineItem(
                            date = date,
                            reminders = remindersForDate,
                            viewModel = viewModel,
                            startIndex = globalCardIndex
                        )
                        globalCardIndex += remindersForDate.size
                    }
                }
            }
        }
    }
}

@Composable
fun VisitTimelineItem(
    date: String,
    reminders: List<VisitReminderEntity>,
    viewModel: EducateViewModel,
    startIndex: Int
) {
    val cardColors = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)
    )
    val timelineLineColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.labelLarge.copy(lineHeight = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant),
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
                        color = timelineLineColor,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = 0f, y = size.height),
                        strokeWidth = strokeWidth
                    )
                }
                .padding(start = 16.dp)
        ) {
            reminders.forEachIndexed { localIndex, reminder ->
                VisitCard(
                    reminder = reminder,
                    containerColor = cardColors[(startIndex + localIndex) % cardColors.size],
                    onDeleteClick = { viewModel.deleteVisitReminder(reminder) }
                )
            }
        }
    }
}

@Composable
fun VisitCard(
    reminder: VisitReminderEntity,
    containerColor: androidx.compose.ui.graphics.Color,
    onDeleteClick: () -> Unit
) {
    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Dr. ${reminder.doctorName}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = reminder.specialty, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Time: ${timeFormat.format(Date(reminder.visitDateTimeMillis))}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Visit", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

private fun formatDate(millis: Long): String {
    val sdf = SimpleDateFormat("MMM\ndd", Locale.getDefault())
    return sdf.format(Date(millis))
}
