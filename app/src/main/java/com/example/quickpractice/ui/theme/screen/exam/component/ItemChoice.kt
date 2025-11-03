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
import com.example.quickpractice.ui.theme.Red
import com.example.quickpractice.ui.theme.screen.exam.component.math.MixedMathText
import com.example.quickpractice.ui.theme.screen.exam.model.Choice

@Composable
fun ItemChoice(
    choice: Choice,
    content: String,
    selectChoice: Choice?,
    correctAnswer: Choice,
    isCorrected: Boolean?,
    isAnswered: Boolean = false,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val corrected = isCorrected == true && selectChoice?.value == choice.value
    val isWrong = isAnswered && selectChoice?.value == choice.value
    val isCorrectForWrong = isAnswered && selectChoice?.value != choice.value && correctAnswer.value == choice.value
    val isSelected = corrected || isWrong

    val colorText = if (corrected || isCorrectForWrong) {
        Green
    } else if (isWrong) {
        Red
    } else {
        Color.Black
    }

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
            selected = isSelected,
            onClick = { if (!isAnswered) onClick() },
            colors = RadioButtonDefaults.colors(
                selectedColor = if (isCorrected == true || isCorrectForWrong) Green else Red,
                unselectedColor = if (isCorrectForWrong) Green else Grey
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${choice.title}. ",
                color = colorText,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
            MixedMathText(
                text = content,
                fontSize = 16.dp,
                color = colorText,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}