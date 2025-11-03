package com.example.quickpractice.ui.theme.screen.exam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quickpractice.ui.theme.screen.exam.component.ItemQuestion
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel
import com.example.quickpractice.util.clickView

@Composable
fun ExamScreen(navController: NavController, viewModel: ExamViewModel = hiltViewModel()) {
    var questionsState by remember { mutableStateOf<List<QuestionModel>>(listOf()) }
    val questions = viewModel.questions.collectAsState().value ?: listOf()

    LaunchedEffect(Unit) {
        viewModel.getArgument(navController)
    }

    LaunchedEffect(questions) {
        questionsState = questions
    }

    Column {
        HeaderExam(navController = navController)

        LazyColumn {
            itemsIndexed(questionsState) { index, question ->
                ItemQuestion(question, onUpdateQuestion = { q ->
                    questionsState = questionsState.toMutableList().also {
                        it[index] = q
                    }
                }, onUpdateExpand = {
                    questionsState = questionsState.toMutableList().also {
                        it[index] = it[index].copy(expand = !it[index].expand)
                    }
                })
            }
        }
    }
}

@Composable
private fun HeaderExam(navController: NavController) {
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .padding(vertical = 20.dp)
    ) {
        Icon(
            Icons.Filled.KeyboardArrowLeft, contentDescription = "Back",
            modifier = Modifier
                .size(40.dp)
                .clickView {
                    navController.popBackStack()
                }
                .padding(start = 10.dp, end = 15.dp),
        )

        Text(
            text = "Câu 1 / 10",
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun ExamScreenPreview() {
    ExamScreen(navController = NavController(LocalContext.current))
}