package com.example.cp.ui.screen.points

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cp.R
import com.example.cp.data.local.entity.DescriptionEntity
import com.example.cp.ui.viewmodel.EducateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionScreen(
    navController: NavController,
    titleId: Int,
    viewModel: EducateViewModel = hiltViewModel()
) {
    val titleWithDetails by viewModel.getDetailsForTitle(titleId).collectAsState(initial = null)

    // Define a list of colors to cycle through for the cards
    val cardColors = listOf(
        Color(0xFFF8E8E8),  // Light Pink
        Color(0xFFE5F8E0), // Light Green
        Color(0xFFE9E7FD), // Light Purple
        Color(0xFFFFF8D6) // Light Yellow
    )

    Scaffold(

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 24.dp, start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    },
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "Icon arrow back"
                )

                Text(
                    text = titleWithDetails?.parent?.title ?: "",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (titleWithDetails == null || titleWithDetails!!.children.isEmpty()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    val pagerState =
                        rememberPagerState(pageCount = { titleWithDetails!!.children.size })

                    Column(modifier = Modifier.fillMaxSize()) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 24.dp) // Add padding so cards don't touch the screen edges
                        ) { pageIndex ->
                            val description = titleWithDetails!!.children[pageIndex]
                            // Pass the cycling color to the page UI
                            DescriptionPageUI(
                                descriptionEntity = description,
                                cardColor = cardColors[pageIndex % cardColors.size]
                            )
                        }

                        PagerIndicator(
                            pagerState = pagerState,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * The UI for a single page in the pager, now wrapped in a colored Card with elevation.
 */
@Composable
fun DescriptionPageUI(descriptionEntity: DescriptionEntity, cardColor: Color) {
    // A Box to center the card within the pager's page
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp), // Give some vertical padding
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp) // This adds the shadow
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (descriptionEntity.title.isNotBlank()) {
                    Text(
                        text = descriptionEntity.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black.copy(alpha = 0.7f) // Use a dark color for readability
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Text(
                    text = descriptionEntity.description,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Start,
                    color = Color.Black.copy(alpha = 0.8f) // Use a dark color for readability
                )
            }
        }
    }
}

@Composable
fun PagerIndicator(pagerState: PagerState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val isSelected = pagerState.currentPage == iteration
            val color by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                animationSpec = tween(durationMillis = 300)
            )
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(if (isSelected) 12.dp else 8.dp)
            )
        }
    }
}