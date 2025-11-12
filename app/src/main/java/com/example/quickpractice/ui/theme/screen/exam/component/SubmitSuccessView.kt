package com.example.quickpractice.ui.theme.screen.exam.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickpractice.ui.theme.Green
import com.example.quickpractice.ui.theme.Grey99
import com.example.quickpractice.ui.theme.Red
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.ExamResultModel
import com.example.quickpractice.ui.theme.screen.exam.model.SubjectModel

@Composable
fun SubmitSuccessView(examResult: ExamResultModel) {
    val sizeCorrect = 16.sp
    val sizeResult = 14.sp
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .padding(15.dp)
    ) {
        Text(
            "Bạn đã hoàn thành bài làm. Bạn có thể quay lại các trang trước để xem đáp án.",
            color = Color.Black,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            modifier = Modifier.padding(end = 30.dp)
        )

        Text(
            "Tổng thể", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.W500,
            modifier = Modifier
                .padding(top = 30.dp)
        )

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Green, fontSize = sizeCorrect)) {
                    append("Đúng ")
                }
                withStyle(style = SpanStyle(color = Grey99, fontSize = sizeCorrect)) {
                    append("/ ")
                }
                withStyle(style = SpanStyle(color = Red, fontSize = sizeCorrect)) {
                    append("Sai ")
                }
                withStyle(style = SpanStyle(color = Grey99, fontSize = sizeCorrect)) {
                    append("/ ")
                }
                withStyle(style = SpanStyle(color = Grey99, fontSize = sizeCorrect)) {
                    append("Chưa làm")
                }
            }, modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(), textAlign = TextAlign.End
        )

        Row(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(examResult.exam.title, color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.W500)

            MultiSegmentProgressBar(
                correctPercent = examResult.percentNumberCorrect(),
                incorrectPercent = examResult.percentNumberWrong(),
                unansweredPercent = examResult.percentNumberUnAnswered(),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.LightGray)
            )

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Green, fontSize = sizeResult)) {
                        append("${examResult.numberCorrectAnswers} ")
                    }
                    withStyle(style = SpanStyle(color = Grey99, fontSize = sizeResult)) {
                        append("/ ")
                    }
                    withStyle(style = SpanStyle(color = Red, fontSize = sizeResult)) {
                        append("${examResult.numberWrongAnswers} ")
                    }
                    withStyle(style = SpanStyle(color = Grey99, fontSize = sizeResult)) {
                        append("/ ")
                    }
                    withStyle(style = SpanStyle(color = Grey99, fontSize = sizeResult)) {
                        append("${examResult.numberUnAnswered()}")
                    }
                }
            )
        }

        Text(
            "Chia sẻ bài làm",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .indication(interactionSource, rememberRipple())
                .padding(top = 20.dp)
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {

                }
                .background(Green, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 10.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun SubmitSuccessViewPreview() {
    val examResult = ExamResultModel(
        id = 1,
        exam = ExamModel(
            id = 1,
            title = "Toán 10",
            subject = SubjectModel(id = 1, nameSubject = "Toán"),
            questions = null,
            durationSeconds = 70,
            createdAt = ""
        ),
        numberCorrectAnswers = 7,
        numberWrongAnswers = 2,
        totalQuestions = 10,
        userId = 1,
        submittedAt = ""
    )
    SubmitSuccessView(examResult = examResult)
}