package com.example.cp.ui.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cp.R
import com.example.cp.data.local.model.SettingItem

@Composable
fun SettingCard(
    item: SettingItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onClick() }, // Makes the card a square
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = item.color),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // Top Section: Icon and Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.1f))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = Color.Black.copy(alpha = 0.8f),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Column {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black.copy(alpha = 0.9f)
                    )
                    Text(
                        text = item.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                }
            }

            // Spacer to push content to top and bottom
            Spacer(modifier = Modifier.weight(1f))

            // Middle Section: Value and Unit
            /*Text(
                text = buildAnnotatedString {
                    *//*withStyle(style = MaterialTheme.typography.displaySmall.toSpanStyle().copy(fontWeight = FontWeight.ExtraBold, color = Color.Black)) {
                        append(item.value)
                    }*//*
                    withStyle(style = MaterialTheme.typography.bodyLarge.toSpanStyle().copy(color = Color.Black.copy(alpha = 0.7f))) {
                        append(" ${item.unit}")
                    }
                }
            )*/

            // Bottom Section: Arrow Button
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                IconButton(
                    onClick = { /* TODO: Handle card action */ },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.8f))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_up),
                        contentDescription = "Go to ${item.title}",
                        tint = item.color,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

