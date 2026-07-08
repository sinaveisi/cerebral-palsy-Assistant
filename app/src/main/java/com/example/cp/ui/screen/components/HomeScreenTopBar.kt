package com.example.cp.ui.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cp.R

@Composable
fun HomeScreenTopBar() {
    Surface(
        color = Color(0xFFDCF2E8),
        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 16.dp,
                    bottom = 32.dp
                ) // Adjusted bottom padding
        ) {
            // Top Icons: Apps, Tune, Search
            TopActions()

            Spacer(modifier = Modifier.height(24.dp))

            // Header Text: Manage Your Files
            HeaderText()
        }
    }
}

@Composable
private fun TopActions() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            "فلج مغزی",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .clip(shape = CircleShape)
                .background(White)
                .padding(8.dp), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_book),
                contentDescription = "Apps"
            )

        }


    }
}

@Composable
private fun HeaderText() {
    Column {
        Text(modifier = Modifier.fillMaxWidth(),
            text = "مدیریت",
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Light
        )
        Text(modifier = Modifier.fillMaxWidth(),
            text = "سلامت فرزندم",
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(modifier = Modifier.fillMaxWidth(),
            text = "آموزش نکات ضروری و حفظ پویایی",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}