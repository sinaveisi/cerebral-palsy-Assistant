package com.example.cp.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cp.R
import com.example.cp.data.local.model.LearningTopic
import com.example.cp.navigation.ScreensRoute
import com.example.cp.ui.screen.components.HomeScreenTopBar
import com.example.cp.ui.screen.components.LearningCard


@Composable
fun HomeScreen(navController: NavController) {
    // This is your original hardcoded list of CATEGORIES
    val learningTopics = listOf(
        LearningTopic(1, "بیماری", Color(0xFFE9E7FD), R.drawable.pic_menu_1),
        LearningTopic(2, "مدیریت سبک زندگی و لزوم مصرف داروها", Color(0xFFE6F5F0), R.drawable.pic_menu_2),
        LearningTopic(3, "درمان و داروهای مورد نیاز", Color(0xFFF8E8E8), R.drawable.pic_menu_3),
        LearningTopic(4, "مداخلات تشخیصی", Color(0xFFE9E7FD), R.drawable.pic_menu_4),
        LearningTopic(5, "اطلاع رسانی", Color(0xFFFFF8D6), R.drawable.pic_menu_5),
        LearningTopic(6, "علائم اولیه CP", Color(0xFFE6F5F0), R.drawable.pic_menu_6),
        LearningTopic(7, "انواع فلج مغزی", Color(0xFFF8E8E8), R.drawable.pic_menu_7),
        LearningTopic(8, "علت فلج مغزی", Color(0xFFFFF8D6), R.drawable.pic_menu_7),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HomeScreenTopBar()
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
            text = "بیا باهم یادبگیریم!",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(learningTopics) { topic ->
                LearningCard(
                    title = topic.title,
                    imageResId = topic.imageResId,
                    backgroundColor = topic.color,
                    modifier = Modifier.clickable { // Handle click to navigate
                        navController.navigate(
                            ScreensRoute.SubTitle.createRoute(
                                categoryId = topic.id,
                                categoryName = topic.title
                            )
                        )
                    }
                )
            }
        }
    }
}