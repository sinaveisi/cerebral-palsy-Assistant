package com.example.cp.ui.screen.setting.bmi

import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cp.util.NetworkUtils
import androidx.core.net.toUri
import com.example.cp.R
import com.example.cp.navigation.ScreensRoute

// A data class to hold the link information neatly.
private data class BmiLink(val text: String, val url: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutBMIScreen(navController: NavController) {
    val context = LocalContext.current

    // Store link data in a clean list. This makes it easy to add or remove links.
    val bmiLinks = listOf(
        BmiLink("محاسبه گر BMI کودک و نوجوان (CDC)", "https://www.cdc.gov/bmi/child-teen-calculator/index.html?unit_switch_radio=metric_units&sex_radio=sex_girl&age_radio=age_years_months&dob_month=0&dob_day=0&dob_year=0&dom_month=0&dom_day=0&dom_year=0&height_radio=height_ft_inches_fraction&height_fraction_inch=0&weight_radio=weight_lbs&weight_fraction_lb=0"),
        BmiLink("محاسبه گر BMI کودک و نوجوان (NHS)", "https://www.nhs.uk/health-assessment-tools/calculate-your-body-mass-index/calculate-bmi-for-children-teenagers"),
        BmiLink("محاسبه گر BMI برای سن های مختلف (WHO)", "https://www.who.int/toolkits/child-growth-standards/standards/body-mass-index-for-age-bmi-for-age"),
        BmiLink("محاسبه گر BMI برای سن 5 تا 19 سال (WHO)", "https://www.who.int/tools/growth-reference-data-for-5to19-years/indicators/bmi-for-age")
    )

    Column(modifier = Modifier.fillMaxSize()) {

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

            Text(text = "لینک های علمی BMI", style = MaterialTheme.typography.titleMedium)


            Image(
                modifier = Modifier.clickable {
                    navController.navigate(ScreensRoute.AboutBMI.route)
                },
                painter = painterResource(id = R.drawable.ic_hyperlink),
                contentDescription = "hyperlink Image"
            )


        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(bmiLinks) { link ->
                HyperLinkItem(text = link.text) {
                    if (NetworkUtils.isOnline(context)) {
                        val intent = Intent(Intent.ACTION_VIEW, link.url.toUri())
                        try {
                            context.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(context, "لطفا ابتدا بر روی دیوایس خود مروگر نصب کنید.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "لطفا ابتدا از اتصال خود به اینترنت مطمئن شوید.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun HyperLinkItem(text: String, onLinkClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onLinkClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f) // Allow text to take up available space
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = "Open Link",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}