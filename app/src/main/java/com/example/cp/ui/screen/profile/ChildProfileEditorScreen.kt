package com.example.cp.ui.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.example.cp.data.local.entity.ChildProfileEntity
import com.example.cp.ui.viewmodel.EducateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildProfileEditorScreen(
    navController: NavController,
    viewModel: EducateViewModel = hiltViewModel()
) {
    val childProfile by viewModel.childProfile.collectAsState()

    // State holders for each text field
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var birthOrder by remember { mutableStateOf("") }
    var insuranceType by remember { mutableStateOf("") }
    var diagnosisDate by remember { mutableStateOf("") }
    var cpType by remember { mutableStateOf("") }
    var gmfcsLevel by remember { mutableStateOf("") }
    var primarySymptoms by remember { mutableStateOf("") }
    var secondarySymptoms by remember { mutableStateOf("") }
    var medications by remember { mutableStateOf("") }
    var treatmentsUsed by remember { mutableStateOf("") }
    var surgicalHistory by remember { mutableStateOf("") }
    var otherConditions by remember { mutableStateOf("") }

    LaunchedEffect(childProfile) {
        childProfile?.let {
            gender = it.gender
            age = it.age
            weight = it.weight
            height = it.height
            birthOrder = it.birthOrder
            insuranceType = it.insuranceType
            diagnosisDate = it.diagnosisDate
            cpType = it.cpType
            gmfcsLevel = it.gmfcsLevel
            primarySymptoms = it.primarySymptoms
            secondarySymptoms = it.secondarySymptoms
            medications = it.medications
            treatmentsUsed = it.treatmentsUsed
            surgicalHistory = it.surgicalHistory
            otherConditions = it.otherConditions
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "پروفایل کودک",
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
                ProfileTextField(label = "جنس", value = gender, onValueChange = { gender = it })
                ProfileTextField(label = "سن", value = age, onValueChange = { age = it })
                ProfileTextField(label = "وزن", value = weight, onValueChange = { weight = it })
                ProfileTextField(label = "قد", value = height, onValueChange = { height = it })
                ProfileTextField(label = "فرزند چندم خانواده", value = birthOrder, onValueChange = { birthOrder = it })
                ProfileTextField(label = "نوع بیمه", value = insuranceType, onValueChange = { insuranceType = it })
                ProfileTextField(label = "تاریخ اولین تشخیص", value = diagnosisDate, onValueChange = { diagnosisDate = it })
                ProfileTextField(label = "نوع فلج مغزی", value = cpType, onValueChange = { cpType = it })
                ProfileTextField(label = "نمره GMFCS", value = gmfcsLevel, onValueChange = { gmfcsLevel = it })
                ProfileTextField(label = "علائم اولیه و ثانویه", value = primarySymptoms, onValueChange = { primarySymptoms = it })
                ProfileTextField(label = "داروهای مصرفی", value = medications, onValueChange = { medications = it })
                ProfileTextField(label = "نوع درمان های استفاده شده", value = treatmentsUsed, onValueChange = { treatmentsUsed = it })
                ProfileTextField(label = "سابقه جراحی", value = surgicalHistory, onValueChange = { surgicalHistory = it })
                ProfileTextField(label = "سایر بیماری های همراه", value = otherConditions, onValueChange = { otherConditions = it })
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    val updatedProfile = ChildProfileEntity(
                        gender = gender,
                        age = age,
                        weight = weight,
                        height = height,
                        birthOrder = birthOrder,
                        insuranceType = insuranceType,
                        diagnosisDate = diagnosisDate,
                        cpType = cpType,
                        gmfcsLevel = gmfcsLevel,
                        primarySymptoms = primarySymptoms,
                        secondarySymptoms = secondarySymptoms,
                        medications = medications,
                        treatmentsUsed = treatmentsUsed,
                        surgicalHistory = surgicalHistory,
                        otherConditions = otherConditions
                    )
                    viewModel.saveChildProfile(updatedProfile)
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