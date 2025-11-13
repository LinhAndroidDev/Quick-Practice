package com.example.quickpractice.ui.theme.screen.exam_history.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickpractice.R
import com.example.quickpractice.ui.theme.Green
import com.example.quickpractice.ui.theme.GreenDark
import com.example.quickpractice.ui.theme.Grey
import com.example.quickpractice.ui.theme.Grey99
import com.example.quickpractice.ui.theme.GreyDark
import com.example.quickpractice.ui.theme.Red
import com.example.quickpractice.ui.theme.component.FooterView
import com.example.quickpractice.ui.theme.component.MultiSegmentProgressBar
import com.example.quickpractice.ui.theme.screen.exam.model.ExamResultModel
import com.example.quickpractice.util.DateUtils

@Composable
fun ItemExamHistory(examHistory: ExamResultModel, seeAgainTap: (Int) -> Unit) {
    val (hour, date) = DateUtils.getHourDate(examHistory.submittedAt)
    val sizeResult = 14.sp
    Row(modifier = Modifier
        .padding(bottom = 3.dp)
        .height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier.padding(start = 10.dp, top = 3.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(hour, color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.W500)

            Text(date, color = GreyDark, fontSize = 10.sp)
        }

        Column(
            modifier = Modifier
                .padding(top = 5.dp, start = 5.dp, end = 5.dp)
                .width(10.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 3.dp)
                    .size(10.dp)
                    .background(Grey, shape = CircleShape)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .width(2.dp)
                    .background(Grey)
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 5.dp, end = 15.dp, bottom = 15.dp)
                .weight(1f)
                .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                .padding(top = 20.dp, start = 15.dp, end = 15.dp, bottom = 15.dp)
        ) {
            Text(
                examHistory.exam.title,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Icon(
                    painter = painterResource(R.drawable.ic_share), contentDescription = "share",
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(23.dp),
                    tint = Green
                )
            }

            Row(
                modifier = Modifier.padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Đã hoàn thành",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W300,
                    modifier = Modifier
                        .background(GreenDark, shape = RoundedCornerShape(25.dp))
                        .padding(vertical = 2.dp, horizontal = 8.dp),
                    textAlign = TextAlign.Center
                )

                Box(modifier = Modifier.weight(1f))

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Green, fontSize = sizeResult)) {
                            append("${examHistory.numberCorrectAnswers} ")
                        }
                        withStyle(style = SpanStyle(color = Grey99, fontSize = sizeResult)) {
                            append("/ ")
                        }
                        withStyle(style = SpanStyle(color = Red, fontSize = sizeResult)) {
                            append("${examHistory.numberWrongAnswers} ")
                        }
                        withStyle(style = SpanStyle(color = Grey99, fontSize = sizeResult)) {
                            append("/ ")
                        }
                        withStyle(style = SpanStyle(color = Grey99, fontSize = sizeResult)) {
                            append("${examHistory.numberUnAnswered()}")
                        }
                    }
                )
            }

            MultiSegmentProgressBar(
                correctPercent = examHistory.percentNumberCorrect(),
                incorrectPercent = examHistory.percentNumberWrong(),
                unansweredPercent = examHistory.percentNumberUnAnswered(),
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 15.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.LightGray)
            )

            FooterView(
                leftTitle = "Xem lại",
                rightTitle = "Học câu sai",
                leftTap = { seeAgainTap.invoke(examHistory.id) },
                rightTap = {

                })
        }
    }
}

@Preview
@Composable
fun PreviewItemExamHistory() {
}