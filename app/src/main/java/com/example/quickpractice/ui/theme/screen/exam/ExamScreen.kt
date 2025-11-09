package com.example.quickpractice.ui.theme.screen.exam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quickpractice.R
import com.example.quickpractice.ui.theme.Orange
import com.example.quickpractice.ui.theme.Red
import com.example.quickpractice.ui.theme.screen.exam.component.DialogConfirmSave
import com.example.quickpractice.ui.theme.screen.exam.component.ItemQuestion
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel
import com.example.quickpractice.util.clickView
import com.example.quickpractice.util.getTimerFormat
import kotlinx.coroutines.delay

private const val MAX_ITEM_PER_PAGE = 3

/**
 * Divide the questions evenly into pages, each page has a maximum of 3 questions.
 * Calculate the number of pages by dividing the number of questions by 3.
 * If the remainder is not 0 after dividing by 3, add 1 more page.
 */
@Composable
fun ExamScreen(navController: NavController, viewModel: ExamViewModel = hiltViewModel()) {
    var questionsState by remember { mutableStateOf<List<QuestionModel>>(listOf()) }
    val questions = viewModel.questions.collectAsState().value ?: listOf()
    val extraPage = if (questionsState.size % MAX_ITEM_PER_PAGE == 0) 0 else 1
    val pagerState =
        rememberPagerState(pageCount = { questionsState.size / MAX_ITEM_PER_PAGE + extraPage })

    LaunchedEffect(Unit) {
        viewModel.getArgument(navController)
    }

    LaunchedEffect(questions) {
        questionsState = questions
    }

    Column {
        HeaderExam(
            navController = navController,
            pageState = pagerState,
            viewModel.durationSeconds.collectAsState().value
        ) {

        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            val count =
                if (page == pagerState.pageCount - 1 && questionsState.size % MAX_ITEM_PER_PAGE != 0) {
                    questionsState.size % MAX_ITEM_PER_PAGE
                } else {
                    3
                }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(count) { index ->
                    val position = index + page * MAX_ITEM_PER_PAGE
                    ItemQuestion(questionsState[position], onUpdateQuestion = { q ->
                        questionsState = questionsState.toMutableList().also {
                            it[position] = q
                        }
                    }, onUpdateExpand = {
                        questionsState = questionsState.toMutableList().also {
                            it[position] = it[position].copy(expand = !it[position].expand)
                        }
                    })
                }
            }
        }
    }
}

@Composable
private fun HeaderExam(navController: NavController, pageState: PagerState, duration: Int, onFinished: () -> Unit = {}) {
    var timeLeft by remember { mutableStateOf(0) }
    var showDialogConfirmSave by remember { mutableStateOf(false) }

    LaunchedEffect(duration) {
        timeLeft = duration
    }

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000)
            timeLeft--
        } else {
            onFinished()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(vertical = 20.dp),
        verticalAlignment = CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_save), contentDescription = "Save",
            modifier = Modifier
                .size(18.dp)
                .clickView {
                    showDialogConfirmSave = true
                }
                .padding(start = 10.dp, end = 15.dp),
            tint = Color.Black
        )

        Text(
            text = "Trang ${pageState.currentPage + 1}",
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier
                .padding(start = 20.dp)
                .weight(1f),
            textAlign = TextAlign.Center
        )

        Icon(
            painter = painterResource(R.drawable.ic_timer), contentDescription = "list",
            modifier = Modifier
                .padding(start = 20.dp)
                .size(20.dp),
            tint = Orange
        )

        Text(
            getTimerFormat(timeLeft),
            color = Red,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 5.dp, end = 20.dp)
        )
    }

    DialogConfirmSave(
        showDialog = showDialogConfirmSave,
        onDismiss = {
            showDialogConfirmSave = false
        },
        onConfirm = {
            navController.popBackStack()
        }
    )
}

@Preview
@Composable
fun ExamScreenPreview() {
    ExamScreen(navController = NavController(LocalContext.current))
}