package com.example.cp.ui.screen.setting.note

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cp.data.local.entity.NoteEntity
import com.example.cp.ui.viewmodel.EducateViewModel

// Data class to represent a priority level with its associated color
private data class PriorityOption(val label: String, val color: Color)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorScreen(
    navController: NavController,
    noteId: Int,
    viewModel: EducateViewModel = hiltViewModel()
) {
    // Define the available priorities and their colors
    val priorities = listOf(
        PriorityOption("معمولی", Color(0xFFFFFFFF)),
        PriorityOption("مهم", MaterialTheme.colorScheme.tertiaryContainer),
        PriorityOption("ضروری", MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f))
    )

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf(priorities.first()) }

    // Fetch note data if we are editing an existing note
    LaunchedEffect(key1 = noteId) {
        if (noteId != -1) {
            viewModel.getNoteById(noteId).collect { note ->
                if (note != null) {
                    title = note.title
                    content = note.content
                    // Find the matching priority option, or default to Normal
                    selectedPriority = priorities.find { it.label == note.priority } ?: priorities.first()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (noteId == -1) "یادداشت جدید" else "ویرایش یادداشت",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.upsertNote(
                            noteId = noteId,
                            title = title,
                            content = content,
                            priority = selectedPriority.label,
                            colorHex = "#${Integer.toHexString(selectedPriority.color.toArgb())}",
                            onNoteSaved = { navController.popBackStack() }
                        )
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save Note")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("عنوان یادداشت", modifier = Modifier.fillMaxWidth()) },
                textStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // -- NEW PRIORITY SELECTOR --
            Text("اهمیت", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                priorities.forEach { priorityOption ->
                    PriorityChip(
                        option = priorityOption,
                        isSelected = selectedPriority == priorityOption,
                        onSelect = { selectedPriority = priorityOption }
                    )
                }
            }
            // -- END OF PRIORITY SELECTOR --

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text(text = "متن یادداشت ...", modifier = Modifier.fillMaxWidth()) },
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 300.dp)
            )
        }
    }
}

@Composable
private fun PriorityChip(
    option: PriorityOption,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .border(1.dp, borderColor, RoundedCornerShape(50))
            .background(backgroundColor)
            .clickable(onClick = onSelect)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = option.label,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}