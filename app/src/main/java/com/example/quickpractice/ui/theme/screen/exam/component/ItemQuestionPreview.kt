package com.example.quickpractice.ui.theme.screen.exam.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickpractice.R
import com.example.quickpractice.ui.theme.screen.exam.model.Choice
import com.example.quickpractice.ui.theme.screen.exam.model.Correct
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel
import com.example.quickpractice.util.clickView

@Composable
fun ItemQuestionPreview(question: QuestionModel, onClick: () -> Unit) {
    Row(modifier = Modifier
        .padding(bottom = 15.dp)
        .fillMaxWidth()
        .clickView {
            onClick.invoke()
        }, verticalAlignment = Alignment.CenterVertically) {
        val answers = question.getAnswers()
        Text(
            "${question.id}.",
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Box(modifier = Modifier.weight(1f)) {
            ChoiceView(answers[Choice.A] ?: Correct.NO_ANSWER, Choice.A)
        }
        Box(modifier = Modifier.weight(1f)) {
            ChoiceView(answers[Choice.B] ?: Correct.NO_ANSWER, Choice.B)
        }
        Box(modifier = Modifier.weight(1f)) {
            ChoiceView(answers[Choice.C] ?: Correct.NO_ANSWER, Choice.C)
        }
        Box(modifier = Modifier.weight(1f)) {
            ChoiceView(answers[Choice.D] ?: Correct.NO_ANSWER, Choice.D)
        }
    }
}

@Composable
private fun ChoiceView(correct: Correct, choice: Choice) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = correct.color, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = choice.title,
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }

        if (correct != Correct.NO_ANSWER) {
            Icon(
                painter = painterResource(if (correct == Correct.CORRECT) R.drawable.ic_correct else R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 2.dp)
                    .size(20.dp),
                tint = correct.color,
            )
        }
    }
}

@Preview
@Composable
fun ItemQuestionPreview_Preview() {

}