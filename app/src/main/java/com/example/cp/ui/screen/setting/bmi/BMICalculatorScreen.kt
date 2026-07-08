package com.example.cp.ui.screen.setting.bmi

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cp.R
import com.example.cp.navigation.ScreensRoute

@Composable
fun BMICalculatorScreen(navController: NavController) {


    val context = LocalContext.current

    var height by remember {
        mutableStateOf("")
    }

    var weight by remember {
        mutableStateOf("")
    }

    var bmi by remember {
        mutableFloatStateOf(0f)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .clickable { navController.popBackStack() }
                    .padding(8.dp)) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "back Icon"
                )
            }

            Text(text = "محاسبه گر BMI", style = MaterialTheme.typography.titleMedium)


            Image(
                modifier = Modifier.clickable {
                    navController.navigate(ScreensRoute.AboutBMI.route)
                },
                painter = painterResource(id = R.drawable.ic_hyperlink),
                contentDescription = "hyperlink Image"
            )


        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            OutlinedTextField(
                value = height,
                onValueChange = { value ->
                    if (value.length <= 3) {

                        height = value.filter { it.isDigit() }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                placeholder = {
                    Text(
                        text = "قد(سانتی متر)",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                textStyle = MaterialTheme.typography.bodyLarge.copy(textDirection = TextDirection.Ltr),
                label = {
                    Text(
                        text = "قد",
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            )

            OutlinedTextField(
                value = weight,
                onValueChange = { value ->
                    if (value.length <= 3) {

                        weight = value.filter { it.isDigit() }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                placeholder = {
                    Text(
                        text = "وزن(کیلوگرم)",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                textStyle = MaterialTheme.typography.bodyLarge.copy(textDirection = TextDirection.Ltr),
                label = {
                    Text(
                        text = "وزن",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(shape = CircleShape)
                    .clickable {
                        if (height
                                .trim()
                                .isBlank() && weight
                                .trim()
                                .isBlank()
                        ) {
                            Toast
                                .makeText(
                                    context,
                                    "لطفا ابتدا وزن و قد خود را وارد کنید",
                                    Toast.LENGTH_SHORT
                                )
                                .show()

                        } else {
                            val heightValue = height.toFloatOrNull()
                            val weightValue = weight.toFloatOrNull()

                            if (heightValue != null && weightValue != null)
                                bmi = calculateBMI(weightValue, heightValue)

                        }
                    },
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_math),
                    contentDescription = "do math"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                if (bmi != 0f) {

                    val textDescription = interpretBMI(bmi)
                    Text(text = "شاخص توده بدنی شما", style = MaterialTheme.typography.bodyMedium)
                    Text(text = bmi.toString(), style = MaterialTheme.typography.titleLarge)

                    Text(text = textDescription, style = MaterialTheme.typography.bodyMedium)
                } else {

                    Image(
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(id = R.drawable.ic_math),
                        contentDescription = "math image"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "لطفا وزن و قد خود را وارد کنید",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }


            }

        }


    }

}

fun calculateBMI(weight: Float, heightCm: Float): Float {
    val heightM = heightCm / 100
    return weight / (heightM * heightM)
}

fun interpretBMI(bmi: Float): String {
    return when {
        bmi < 18.5 -> "شاخص توده بدنی شما نشان‌دهنده کمبود وزن است. بهتر است با یک متخصص تغذیه مشورت کنید تا برنامه‌ای مناسب برای افزایش وزن سالم داشته باشید."
        bmi in 18.5..24.9 -> "شاخص توده بدنی شما نشان‌دهنده وزن سالم است. با حفظ این وزن، خطر ابتلا به بیماری‌های جدی کاهش می‌یابد. به تغذیه مناسب و فعالیت بدنی منظم ادامه دهید."
        bmi in 25.0..29.9 -> "شاخص توده بدنی شما نشان‌دهنده اضافه وزن است. بهتر است با تغییرات کوچکی در رژیم غذایی و افزایش فعالیت بدنی، وزن خود را کاهش دهید تا به محدوده سالم برسید."
        bmi in 30.0..34.9 -> "شاخص توده بدنی شما نشان‌دهنده چاقی است. این وضعیت می‌تواند خطر ابتلا به بیماری‌های قلبی، دیابت و سایر مشکلات سلامتی را افزایش دهد. مشاوره با یک متخصص تغذیه و پزشک می‌تواند به شما در کاهش وزن کمک کند."
        bmi in 35.0..39.9 -> "شاخص توده بدنی شما نشان‌دهنده چاقی شدید است. این وضعیت می‌تواند به طور جدی سلامت شما را تهدید کند. بهتر است با یک تیم پزشکی برای کاهش وزن و بهبود سلامت خود همکاری کنید."
        else -> "شاخص توده بدنی شما نشان‌دهنده چاقی مفرط است. این وضعیت بسیار خطرناک است و نیاز به مداخله فوری پزشکی دارد. لطفاً با یک متخصص تغذیه و پزشک برای برنامه کاهش وزن و بهبود سلامت خود مشورت کنید."
    }
}
