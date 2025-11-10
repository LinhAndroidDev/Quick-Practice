package com.example.quickpractice.ui.theme.screen.exam.model

import androidx.compose.ui.graphics.Color
import com.example.quickpractice.ui.theme.Green
import com.example.quickpractice.ui.theme.Grey99
import com.example.quickpractice.ui.theme.Red

enum class Correct(val color: Color) {
    CORRECT(Green),
    INCORRECT(Red),
    NO_ANSWER(Grey99)
}