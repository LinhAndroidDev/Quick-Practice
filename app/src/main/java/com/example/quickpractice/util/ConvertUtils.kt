package com.example.quickpractice.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

object ConvertUtils {
    @Composable
    fun PxToDp(pxValue: Float): Dp {
        val density = LocalDensity.current
        return with(density) { pxValue.toDp() }
    }

    @Composable
    fun DpToPx(dp: Dp): Float {
        val density = LocalDensity.current
        return with(density) { dp.toPx() }
    }
}