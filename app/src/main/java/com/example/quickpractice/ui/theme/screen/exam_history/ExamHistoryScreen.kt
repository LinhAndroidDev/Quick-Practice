package com.example.quickpractice.ui.theme.screen.exam_history

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.quickpractice.ui.theme.component.AnimatedLoading
import com.example.quickpractice.ui.theme.component.ShadowCommon
import com.example.quickpractice.ui.theme.screen.exam_history.component.ItemExamHistory
import com.example.quickpractice.util.clickView

@Composable
fun ExamHistoryScreen(navController: NavController, viewModel: ExamHistoryViewModel = hiltViewModel()) {
    val examHistories = viewModel.examHistories.collectAsState().value ?: listOf()
    val examHistoryState = viewModel.state.collectAsState().value
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchExamHistories()
    }

    LaunchedEffect(examHistoryState) {
        when(examHistoryState) {
            is ApiStateExamHistory.Loading -> {
                isLoading = true
            }

            is ApiStateExamHistory.Success -> {}

            is ApiStateExamHistory.Failure -> {
                Toast.makeText(context, examHistoryState.message, Toast.LENGTH_SHORT).show()
            }

            else -> {
                isLoading = false
            }
        }
    }

    Box {
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
                        ItemExamHistory(examHistory) { examHistoryId ->
                            viewModel.fetchExamHistory(navController, examHistoryId)
                        }
                    }
                }
            }
        }

        AnimatedLoading(isLoading)
    }
}

@Preview
@Composable
fun ExamHistoryScreenPreview() {
    ExamHistoryScreen(navController = NavController(LocalContext.current))
}