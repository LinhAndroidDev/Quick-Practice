package com.example.quickpractice.util

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.quickpractice.ui.theme.Grey

@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
fun Modifier.clickView(onClick: () -> Unit): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    return Modifier
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

@Composable
fun SvgImage(url: String) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .build(),
        imageLoader = imageLoader,
        contentDescription = null
    )
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