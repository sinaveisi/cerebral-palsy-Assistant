package com.example.cp.ui.screen.setting.visit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cp.R
import com.example.cp.ui.viewmodel.EducateViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetVisitReminderScreen(
    navController: NavController,
    viewModel: EducateViewModel = hiltViewModel()
) {
    var doctorName by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(is24Hour = true)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "تنظیم یادآور ویزیت",
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
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WavyTopSection(icon = {
                Image(
                    painter = painterResource(R.drawable.ic_alarm_clock),
                    contentDescription = "",
                    modifier = Modifier.size(150.dp)
                )
            })

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                OutlinedTextField(
                    value = doctorName,
                    onValueChange = { doctorName = it },
                    label = { Text("Doctor's Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = specialty,
                    onValueChange = { specialty = it },
                    label = { Text("Specialty") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location / Clinic") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Select Date & Time", fontWeight = FontWeight.SemiBold)
                DateTimeInputRow(
                    dateMillis = datePickerState.selectedDateMillis,
                    hour = timePickerState.hour,
                    minute = timePickerState.minute,
                    onDateClick = { showDatePicker = true },
                    onTimeClick = { showTimePicker = true }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val calendar = Calendar.getInstance().apply {
                        timeInMillis =
                            datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        set(Calendar.MINUTE, timePickerState.minute)
                    }
                    viewModel.addVisitReminder(
                        doctorName,
                        specialty,
                        location,
                        calendar.timeInMillis
                    )
                    navController.popBackStack()
                },
                enabled = doctorName.isNotBlank() && specialty.isNotBlank() && datePickerState.selectedDateMillis != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .height(50.dp)
            ) {
                Text("Done", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = { TextButton(onClick = { showDatePicker = false }) { Text("OK") } }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            onConfirm = { showTimePicker = false },
            timePickerState = timePickerState
        )
    }
}

@Composable
private fun DateTimeInputRow(
    dateMillis: Long?,
    hour: Int,
    minute: Int,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault()) }
    val timeFormat = String.format(Locale.US, "%02d:%02d", hour, minute)
    val dateText = if (dateMillis != null) dateFormat.format(Date(dateMillis)) else "Select Date"

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = dateText,
            onValueChange = {},
            modifier = Modifier
                .weight(2f)
                .clickable(onClick = onDateClick),
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(disabledTextColor = MaterialTheme.colorScheme.onSurface)
        )
        OutlinedTextField(
            value = timeFormat,
            onValueChange = {},
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onTimeClick),
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(disabledTextColor = MaterialTheme.colorScheme.onSurface)
        )
    }
}

// --- THE FIX: ADD THESE COMPOSABLES TO THE FILE ---

@Composable
private fun WavyTopSection(icon: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(WavyShape())
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}

class WavyShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                reset()
                lineTo(x = 0f, y = size.height * 0.8f)
                quadraticBezierTo(
                    x1 = size.width / 2,
                    y1 = size.height,
                    x2 = size.width,
                    y2 = size.height * 0.8f
                )
                lineTo(x = size.width, y = 0f)
                close()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    timePickerState: TimePickerState,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TimePicker(state = timePickerState)
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) { Text("Cancel") }
        }
    )
}