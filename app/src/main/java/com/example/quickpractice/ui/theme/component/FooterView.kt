package com.example.quickpractice.ui.theme.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickpractice.ui.theme.Green

@Composable
fun FooterView(leftTitle: String, rightTitle: String, leftTap: () -> Unit = {}, rightTap: () -> Unit = {}, padding: Dp = 0.dp) {
    val is1 = remember { MutableInteractionSource() }
    val is2 = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White, shape = RoundedCornerShape(
                    bottomStart = 8.dp,
                    bottomEnd = 8.dp
                )
            )
            .padding(padding)
    ) {
        Text(
            leftTitle,
            color = Green,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(end = 10.dp)
                .indication(is1, rememberRipple())
                .weight(1f)
                .clickable(
                    interactionSource = is1,
                    indication = null
                ) {
                    leftTap.invoke()
                }
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = Green, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 10.dp),
            textAlign = TextAlign.Center,
        )

        Text(
            rightTitle,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .indication(is2, rememberRipple())
                .weight(1f)
                .clickable(
                    interactionSource = is2,
                    indication = null
                ) {
                    rightTap.invoke()
                }
                .background(Green, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 10.dp),
            textAlign = TextAlign.Center
        )
    }
}