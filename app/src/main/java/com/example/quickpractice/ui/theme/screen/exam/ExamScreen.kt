package com.example.quickpractice.ui.theme.screen.exam

import HeaderExam
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quickpractice.ui.theme.component.AnimatedLoading
import com.example.quickpractice.ui.theme.screen.exam.component.ItemQuestion
import com.example.quickpractice.ui.theme.screen.exam.component.LastPageView
import com.example.quickpractice.ui.theme.screen.exam.model.ExamResultModel
import com.example.quickpractice.ui.theme.screen.exam.model.ExamType
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel

private const val MAX_ITEM_PER_PAGE = 3

/**
 * Divide the questions evenly into pages, each page has a maximum of 3 questions.
 * Calculate the number of pages by dividing the number of questions by 3.
 * If the remainder is not 0 after dividing by 3, add 1 more page.
 */
@Composable
fun ExamScreen(navController: NavController, viewModel: ExamViewModel = hiltViewModel()) {
    val examType = viewModel.examType.collectAsState().value
    var examTypeState by remember { mutableStateOf(examType) }
    var questionsState by remember { mutableStateOf<List<QuestionModel>>(listOf()) }
    val questions = viewModel.questions.collectAsState().value ?: listOf()
    val extraPage = if (questionsState.size % MAX_ITEM_PER_PAGE == 0) 0 else 1
    val lastPage = if (examTypeState == ExamType.PRACTICE) 1 else 0
    val pagerState =
        rememberPagerState(pageCount = { questionsState.size / MAX_ITEM_PER_PAGE + extraPage + lastPage })
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var examResultState by remember { mutableStateOf<ExamResultModel?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var pageCurrent by remember { mutableIntStateOf(0) }
    val isSubmitted = viewModel.isSubmitted.collectAsState().value
    var isSubmittedState by remember { mutableStateOf(isSubmitted) }

    LaunchedEffect(state) {
        when (state) {
            is ApiState.Success -> {
                isLoading = false
                examResultState = (state as ApiState.Success).data
            }

            is ApiState.Failure -> {
                isLoading = false
                Toast.makeText(context, (state as ApiState.Failure).message, Toast.LENGTH_SHORT).show()
            }

            is ApiState.Loading -> {
                isLoading = true
            }

            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getArgument(navController)
    }

    LaunchedEffect(questions) {
        questionsState = questions
    }

    LaunchedEffect(examType) {
        examTypeState = examType
    }

    LaunchedEffect(pageCurrent) {
        pagerState.animateScrollToPage(pageCurrent)
    }

    LaunchedEffect(isSubmitted) {
        isSubmittedState = isSubmitted
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            HeaderExam(
                navController = navController,
                examTypeState,
                pageState = pagerState,
                viewModel.durationSeconds.collectAsState().value,
                questionsState,
                isSubmitted = isSubmitted || examTypeState == ExamType.HISTORY,
                onSubmit = {
                    viewModel.submitExam(questionsState)
                },
                onFinished = {

                },
                onTabQuestion = { index ->
                    val lastPage = if ((index + 1) % MAX_ITEM_PER_PAGE != 0) 1 else 0
                    val position = (index + 1) / MAX_ITEM_PER_PAGE + lastPage
                    pageCurrent = if (examType == ExamType.PRACTICE) {
                        position - 1
                    } else {
                        position
                    }
                }
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                if (page < pagerState.pageCount - 1 || examTypeState == ExamType.HISTORY) {
                    val count =
                        if (page == pagerState.pageCount - 1 - lastPage && questionsState.size % MAX_ITEM_PER_PAGE != 0) {
                            questionsState.size % MAX_ITEM_PER_PAGE
                        } else {
                            3
                        }
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(count) { index ->
                            val position = index + page * MAX_ITEM_PER_PAGE
                            ItemQuestion(
                                questionsState[position],
                                isHistory = examType == ExamType.HISTORY || isSubmittedState,
                                onUpdateQuestion = { q ->
                                    questionsState = questionsState.toMutableList().also {
                                        it[position] = q
                                    }
                                },
                                onUpdateExpand = {
                                    questionsState = questionsState.toMutableList().also {
                                        it[position] =
                                            it[position].copy(expand = !it[position].expand)
                                    }
                                })
                        }
                    }
                } else {
                    // Last page: submit button
                    LastPageView(examResultState) {
                        viewModel.submitExam(questionsState)
                    }
                }
            }
        }

        AnimatedLoading(isLoading = isLoading)
    }
}

@Preview
@Composable
fun ExamScreenPreview() {
    ExamScreen(navController = NavController(LocalContext.current))
}