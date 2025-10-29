package com.example.quickpractice.ui.theme.screen.exam.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickpractice.ui.theme.Blue
import com.example.quickpractice.ui.theme.Grey
import com.example.quickpractice.ui.theme.screen.exam.model.Choice
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel

@Composable
fun ItemQuestion(question: QuestionModel) {
    var expanded by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }
    var choices by remember { mutableStateOf(arrayListOf(false, false, false, false)) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false,
                ambientColor = Grey.copy(alpha = 0.4f),
                spotColor = Grey.copy(alpha = 0.4f)
            )
            .indication(interactionSource, rememberRipple())
            .clickable(interactionSource = interactionSource, indication = null) {
                expanded = !expanded
            },
        colors = CardDefaults.cardColors(containerColor = Blue)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier
                    .weight(1f)
                    .padding(end = 30.dp)) {
                    Text(
                        text = "${question.id}.",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = question.content ?: "",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }
                Icon(
                    Icons.Filled.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .rotate(if (expanded) 0f else 180f),
                    tint = Color.White
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                        .background(color = Color.White)
                        .padding(8.dp)
                ) {
                    val items = listOf(
                        Choice.A to question.optionA,
                        Choice.B to question.optionB,
                        Choice.C to question.optionC,
                        Choice.D to question.optionD,
                    )

                    items.forEach { (choice, text) ->
                        ItemChoice(choice.title, text, choices[choice.value]) {
                            choices = List(choices.size) { index ->
                                index == choice.value
                            }.toCollection(ArrayList())
                        }
                    }
                }
            }
        }
    }
}