package com.example.quickpractice.ui.theme.screen.exam.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickpractice.ui.theme.Green
import com.example.quickpractice.ui.theme.Grey
import com.example.quickpractice.ui.theme.screen.exam.component.math.MixedMathText
import com.example.quickpractice.ui.theme.screen.exam.model.Choice
import com.example.quickpractice.ui.theme.screen.exam.model.Correct

@Composable
fun ItemChoice(
    choice: Choice,
    correct: Correct,
    content: String,
    selectChoice: Choice?,
    isAnswered: Boolean = false,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clickable(interactionSource = interactionSource, indication = null) {
                if (!isAnswered) onClick()
            }
            .indication(interactionSource, rememberRipple()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectChoice?.value == choice.value,
            onClick = { if (!isAnswered) onClick() },
            colors = RadioButtonDefaults.colors(
                selectedColor = correct.color,
                unselectedColor = if (correct == Correct.NO_ANSWER) Grey else Green
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${choice.title}. ",
                color = if (correct == Correct.NO_ANSWER) Color.Black else correct.color,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
            MixedMathText(
                text = content,
                fontSize = 16.dp,
                color = if (correct == Correct.NO_ANSWER) Color.Black else correct.color,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}