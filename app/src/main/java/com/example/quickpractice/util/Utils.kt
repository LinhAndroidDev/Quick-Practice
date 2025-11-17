package com.example.quickpractice.util

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.quickpractice.ui.theme.Grey

@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
fun Modifier.clickView(onClick: () -> Unit): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    return this
        .indication(interactionSource, rememberRipple())
        .clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            onClick()
        }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
fun Modifier.shadowCommon() : Modifier {
    return this.shadow(
        elevation = 10.dp,
        shape = RoundedCornerShape(16.dp),
        clip = false,
        ambientColor = Grey.copy(alpha = 0.4f),
        spotColor = Grey.copy(alpha = 0.4f)
    )
}

@SuppressLint("RememberInComposition")
@Composable
fun UnFocusKeyBoardView(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
                focusManager.clearFocus() // ➜ Ẩn bàn phím
            }) {
        content.invoke()
    }
}

@SuppressLint("DefaultLocale")
fun getTimerFormat(totalSeconds: Int): String {
    val hour = totalSeconds / 3600
    if (hour > 0) {
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d:%02d", hour, minutes, seconds)
    }
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}