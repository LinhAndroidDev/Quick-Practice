package com.example.quickpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.quickpractice.ui.theme.QuickPracticeTheme
import com.example.quickpractice.ui.theme.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickPracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
                    Column(modifier = Modifier.padding(contentPadding)) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}