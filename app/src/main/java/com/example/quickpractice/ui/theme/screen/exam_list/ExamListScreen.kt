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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quickpractice.R
import com.example.quickpractice.ui.theme.Green
import com.example.quickpractice.ui.theme.GreenLight
import com.example.quickpractice.ui.theme.Grey
import com.example.quickpractice.ui.theme.GreyLight
import com.example.quickpractice.ui.theme.navigation.Route
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
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

@Composable
fun ItemExam(exam: ExamModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clickView { onClick() },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = exam.title,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                )

                Row(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .background(color = GreenLight)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "2025 Format",
                            color = Green,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W500,
                        )

                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.ic_list),
                            contentDescription = "list",
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(16.dp),
                            tint = Grey
                        )
                        Text(
                            text = "Chưa làm",
                            color = Grey,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
            Icon(
                painter = painterResource(R.drawable.ic_history), contentDescription = "list",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(18.dp),
                tint = Color.Black
            )

        }
    }
}

@Preview
@Composable
fun ExamListScreenPreview() {
    ExamListScreen(navController = NavController(LocalContext.current))
}

