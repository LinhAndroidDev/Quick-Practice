package com.example.quickpractice.ui.theme.screen.exam.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.quickpractice.ui.theme.Blue
import com.example.quickpractice.ui.theme.Grey
import com.example.quickpractice.ui.theme.screen.exam.component.math.MixedMathText
import com.example.quickpractice.ui.theme.screen.exam.model.Choice
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel
import com.example.quickpractice.util.clickView

@Composable
fun ItemQuestion(
    question: QuestionModel,
    onUpdateExpand: () -> Unit,
    onUpdateQuestion: (QuestionModel) -> Unit
) {

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
            ),
        colors = CardDefaults.cardColors(containerColor = Blue)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .clickView {
                        onUpdateExpand.invoke()
                    }
                    .padding(vertical = 16.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 30.dp)
                ) {
                    Text(
                        text = "${question.id}.",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    MixedMathText(
                        text = question.content ?: "",
                        fontSize = 16.dp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.weight(1f)
                    )
                }
                Icon(
                    Icons.Filled.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .rotate(if (question.expand) 0f else 180f),
                    tint = Color.White
                )
            }

            AnimatedVisibility(visible = question.expand) {
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

                    if (question.contentImage?.isNotEmpty() == true) {
                        AsyncImage(
                            model = question.contentImage, // URL ảnh
                            contentDescription = "Ảnh demo",
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    items.forEach { (choice, text) ->
                        ItemChoice(
                            choice = choice,
                            content = text,
                            selectChoice = question.answer,
                            correctAnswer = Choice.fromValue(question.correctAnswer),
                            isCorrected = question.isCorrect(),
                            isAnswered = question.isAnswered(),
                        ) {
                            onUpdateQuestion.invoke(question.copyWith(answer = choice))
                        }
                    }

                    if (question.answer != null) {
                        AnswerView(
                            correctAnswer = Choice.fromValue(question.correctAnswer),
                            explain = question.explanation,
                            imageUrl = question.explanationImage
                        )
                    }
                }
            }
        }
    }
}