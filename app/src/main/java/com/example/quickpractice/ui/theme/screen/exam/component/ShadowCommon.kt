package com.example.quickpractice.ui.theme.screen.exam.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShadowCommon() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x14000000),
                        Color(0x00FFFFFF)
                    )
                )
            )
    ) { }
}