package com.example.cp.ui.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cp.R
import com.example.cp.data.local.entity.ParentProfileEntity
import com.example.cp.ui.viewmodel.EducateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentProfileEditorScreen(
    navController: NavController,
    viewModel: EducateViewModel = hiltViewModel()
) {
    val parentProfile by viewModel.parentProfile.collectAsState()

    // State holders for each text field
    var age by remember { mutableStateOf("") }
    var educationLevel by remember { mutableStateOf("") }
    var jobStatus by remember { mutableStateOf("") }
    var maritalStatus by remember { mutableStateOf("") }
    var monthlyIncome by remember { mutableStateOf("") }
    var residence by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var familyHistory by remember { mutableStateOf("") }
    var careHoursPerDay by remember { mutableStateOf("") }

    // This effect will run once when the profile data is loaded from the database
    // and populate the fields.
    LaunchedEffect(parentProfile) {
        parentProfile?.let {
            age = it.age
            educationLevel = it.educationLevel
            jobStatus = it.jobStatus
            maritalStatus = it.maritalStatus
            monthlyIncome = it.monthlyIncome
            residence = it.residence
            contactNumber = it.contactNumber
            familyHistory = it.familyHistory
            careHoursPerDay = it.careHoursPerDay
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "پروفایل والدین",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
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
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileTextField(label = "سن", value = age, onValueChange = { age = it })
                ProfileTextField(label = "سطح تحصیلات", value = educationLevel, onValueChange = { educationLevel = it })
                ProfileTextField(label = "شغل", value = jobStatus, onValueChange = { jobStatus = it })
                ProfileTextField(label = "وضعیت تاهل", value = maritalStatus, onValueChange = { maritalStatus = it })
                ProfileTextField(label = "درآمد ماهیانه خانواده", value = monthlyIncome, onValueChange = { monthlyIncome = it })
                ProfileTextField(label = "محل سکونت", value = residence, onValueChange = { residence = it })
                ProfileTextField(label = "شماره تماس", value = contactNumber, onValueChange = { contactNumber = it })
                ProfileTextField(label = "سابقه فلج مغزی در خانواده", value = familyHistory, onValueChange = { familyHistory = it })
                ProfileTextField(label = "ساعات مراقبت از کودک در روز", value = careHoursPerDay, onValueChange = { careHoursPerDay = it })
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    val updatedProfile = ParentProfileEntity(
                        age = age,
                        educationLevel = educationLevel,
                        jobStatus = jobStatus,
                        maritalStatus = maritalStatus,
                        monthlyIncome = monthlyIncome,
                        residence = residence,
                        contactNumber = contactNumber,
                        familyHistory = familyHistory,
                        careHoursPerDay = careHoursPerDay
                    )
                    viewModel.saveParentProfile(updatedProfile)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp)
            ) {
                Text("ذخیره")
            }
        }
    }
}

// A reusable text field for the forms
@Composable
fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    )
}