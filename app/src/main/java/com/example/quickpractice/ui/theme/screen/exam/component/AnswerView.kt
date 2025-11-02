package com.example.quickpractice.ui.theme.screen.exam.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.quickpractice.ui.theme.BlueFF
import com.example.quickpractice.ui.theme.GreyDark
import com.example.quickpractice.ui.theme.GreyEF
import com.example.quickpractice.ui.theme.GreyFA
import com.example.quickpractice.ui.theme.screen.exam.component.math.MixedMathText
import com.example.quickpractice.ui.theme.screen.exam.model.Choice

@Composable
fun AnswerView(correctAnswer: Choice, explain: String?, imageUrl: String?) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            "Đáp án đúng là ${correctAnswer.title}",
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.padding(top = 10.dp)
        )

        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .background(color = GreyFA)
                .clip(shape = RoundedCornerShape(6.dp))
                .border(width = 1.dp, color = GreyEF, shape = RoundedCornerShape(6.dp))
                .padding(10.dp)
        ) {
            Text(
                "\uD83D\uDCA1 Giải thích:",
                color = BlueFF,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier.padding(top = 5.dp)
            )

            if (explain?.isNotEmpty() == true) {
                MixedMathText(
                    text = explain,
                    color = GreyDark,
                    fontSize = 14.dp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                )
            }

            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl, // URL ảnh
                    contentDescription = "Ảnh giải thích",
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun AnswerViewPreview() {
    AnswerView(Choice.A, "Đây là phần giải thích đáp án.", null)
}