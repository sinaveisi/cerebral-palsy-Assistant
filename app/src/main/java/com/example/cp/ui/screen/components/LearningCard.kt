package com.example.cp.ui.screen.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cp.R

@Composable
fun LearningCard(
    title: String,
    @DrawableRes imageResId: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = modifier.height(200.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Decorative background image
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd)
            )

            // Main content column
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(20.dp)
                    .fillMaxWidth(0.4f) // Take up 60% of the width
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }

            // Start Learning Button
            StartLearningButton(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
private fun StartLearningButton(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(50), // Pill shape
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = "شروع یادگیری",
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(12.dp))
            // Play Button
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .padding(7.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_play),
                    contentDescription = "Play"
                )
            }
        }
    }
}