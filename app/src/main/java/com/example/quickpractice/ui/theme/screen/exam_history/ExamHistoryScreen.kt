package com.example.quickpractice.ui.theme.screen.exam_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quickpractice.ui.theme.GreyLight
import com.example.quickpractice.ui.theme.component.ShadowCommon
import com.example.quickpractice.ui.theme.screen.exam_history.component.ItemExamHistory
import com.example.quickpractice.util.clickView
import com.example.quickpractice.util.shadowCommon

@Composable
fun ExamHistoryScreen(navController: NavController, viewModel: ExamHistoryViewModel = hiltViewModel()) {
     val examHistories = viewModel.examHistories.collectAsState().value ?: listOf()

    LaunchedEffect(Unit) {
        viewModel.fetchExamHistories()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GreyLight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(top = 20.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.KeyboardArrowLeft, contentDescription = "Back",
                modifier = Modifier
                    .size(60.dp)
                    .clickView {
                        navController.popBackStack()
                    }
                    .padding(start = 10.dp, end = 15.dp),
            )

            Text(
                text = "Lịch sử",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
            )
        }

        ShadowCommon()

        LazyColumn {
            itemsIndexed(examHistories) { index, examHistory ->
                Column(modifier = Modifier.padding(top = if (index == 0) 10.dp else 0.dp)) {
                    ItemExamHistory(examHistory)
                }
            }
        }
    }
}

@Preview
@Composable
fun ExamHistoryScreenPreview() {
    ExamHistoryScreen(navController = NavController(LocalContext.current))
}