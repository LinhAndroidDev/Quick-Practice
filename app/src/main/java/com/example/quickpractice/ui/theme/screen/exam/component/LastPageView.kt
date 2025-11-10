package com.example.quickpractice.ui.theme.screen.exam.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickpractice.ui.theme.Green

@Composable
fun LastPageView(submit: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Bạn đang ở trang cuối. Bạn có muốn nộp bài không?",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = submit,
            colors = ButtonDefaults.buttonColors(
                containerColor = Green,
                contentColor = Green
            ),
            shape = RoundedCornerShape(25),
            modifier = Modifier
                .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth()
        ) {
            Text(
                "Nộp bài", color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
            )
        }
    }
}

@Preview
@Composable
fun LastPageViewPreview() {
    LastPageView()
}