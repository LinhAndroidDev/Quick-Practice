package com.example.quickpractice.ui.theme.screen.exam_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quickpractice.ui.theme.GreyLight
import com.example.quickpractice.ui.theme.screen.exam_list.component.ItemExam
import com.example.quickpractice.util.clickView

@Composable
fun ExamListScreen(navController: NavController, viewModel: ExamListViewModel = hiltViewModel()) {
    val exams = viewModel.exams.collectAsState().value ?: listOf()
    val title = viewModel.title.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getArgument(navController)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GreyLight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
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
                text = title,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
            )
        }

        LazyColumn {
            items(exams) { exam ->
                Column(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                ) {
                    ItemExam(exam) {
                        viewModel.goToExam(navController, exam)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ExamListScreenPreview() {
    ExamListScreen(navController = NavController(LocalContext.current))
}

