package com.example.cp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cp.navigation.AppNavigation
import com.example.cp.ui.screen.MainScreen
import com.example.cp.ui.theme.CPTheme
import com.example.cp.ui.viewmodel.EducateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CPTheme {

                val viewModel: EducateViewModel = hiltViewModel()
                // MainScreen contains the Scaffold with the bottom bar
                AppNavigation(viewModel)
            }
        }
    }
}
