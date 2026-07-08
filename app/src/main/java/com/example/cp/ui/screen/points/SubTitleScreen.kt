package com.example.cp.ui.screen.points

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.cp.R
import com.example.cp.navigation.ScreensRoute
import com.example.cp.ui.screen.components.TopicCard
import com.example.cp.ui.viewmodel.EducateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubTitleScreen(
    navController: NavController,
    categoryId: Int,
    categoryName: String,
    viewModel: EducateViewModel
) {
    val titles by viewModel.getTitlesForCategory(categoryId).collectAsState(initial = emptyList())

    val cardColors = listOf(
        Color(0xFFF8E8E8),  // Light Pink
        Color(0xFFE5F8E0), // Light Green
        Color(0xFFE9E7FD), // Light Purple
        Color(0xFFFFF8D6) // Light Yellow
    )

    Scaffold { paddingValues ->
        if (titles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Use a scrollable Column to contain the custom layout
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(modifier = Modifier.clickable{
                        navController.popBackStack()
                    },
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = "Icon arrow back"
                    )

                    Text(
                        text = categoryName,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                titles.forEachIndexed { index, titleEntity ->
                    TopicCard(
                        title = titleEntity.title,
                        backgroundColor = cardColors[index % cardColors.size],
                        modifier = Modifier
                            // 1. Set the zIndex so cards draw on top of previous ones
                            .zIndex(index.toFloat())
                            // 2. Apply a negative offset to make the cards overlap
                            .offset(y = (index).dp)
                            // 3. Apply a slight, alternating rotation
                            .rotate(if (index % 2 == 0) -2f else 2f)
                            .clickable {
                                navController.navigate(
                                    ScreensRoute.Description.createRoute(titleId = titleEntity.titleId)
                                )
                            }
                    )
                }
            }
        }
    }
}