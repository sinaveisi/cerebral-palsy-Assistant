package com.example.cp.ui.screen.setting.medicine

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.platform.LocalContext
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
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetMedicineReminderScreen(
    navController: NavController,
    viewModel: EducateViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var showTimePicker by remember { mutableStateOf(false) }
    // 1. Set the time picker to 24-hour format
    val timePickerState =
        rememberTimePickerState(is24Hour = true, initialHour = 8, initialMinute = 0)
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Notification permission denied.", Toast.LENGTH_LONG).show()
            }
        }
    )

    // Request permission when the screen is first composed
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "تنظیم یادآور دارو",
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
            // Top decorative shape with the image
            WavyTopSection()

            // Form Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Medication Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = dosage,
                    onValueChange = { dosage = it },
                    label = { Text("Dosage (e.g., 1 capsule)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Select Time", fontWeight = FontWeight.SemiBold)
                TimeInputRow(
                    hour = timePickerState.hour,
                    minute = timePickerState.minute,
                    onClick = { showTimePicker = true }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Done Button
            Button(
                onClick = {
                    viewModel.addReminder(
                        hour = timePickerState.hour,
                        minute = timePickerState.minute,
                        name = name,
                        dosage = dosage
                    )
                    navController.popBackStack()
                },
                enabled = name.isNotBlank() && dosage.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .height(50.dp)
            ) {
                Text("Done", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
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
private fun WavyTopSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(WavyShape())
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_alarm_clock),
            contentDescription = "Alarm Clock",
            modifier = Modifier.size(150.dp)
        )
    }
}

@Composable
private fun TimeInputRow(hour: Int, minute: Int, onClick: () -> Unit) {
    // 2. Format the time string for 24-hour display
    val formattedTime = String.format(Locale.US, "%02d:%02d", hour, minute)

    OutlinedTextField(
        value = formattedTime,
        onValueChange = { },
        label = { Text("Selected Time") },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        enabled = false, // Disable text input, make it clickable only
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

// A custom shape to create the wavy top background
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