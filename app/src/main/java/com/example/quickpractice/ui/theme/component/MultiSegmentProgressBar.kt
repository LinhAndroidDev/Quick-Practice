package com.example.quickpractice.ui.theme.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.quickpractice.ui.theme.Green
import com.example.quickpractice.ui.theme.Grey99
import com.example.quickpractice.ui.theme.Red

@Composable
fun MultiSegmentProgressBar(
    correctPercent: Float,
    incorrectPercent: Float,
    unansweredPercent: Float,
    modifier: Modifier
) {
    // Tổng 3 phần phải bằng 1.0f
    val total = correctPercent + incorrectPercent + unansweredPercent
    require(total <= 1.0f) { "Tổng phần trăm không được vượt quá 1.0f" }

    Row(modifier = modifier) {
        if (correctPercent > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(correctPercent)
                    .background(Green) // xanh lá đúng
            )
        }
        if (incorrectPercent > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(incorrectPercent)
                    .background(Red) // đỏ sai
            )
        }
        if (unansweredPercent > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(unansweredPercent)
                    .background(Grey99) // xám chưa trả lời
            )
        }
    }
}