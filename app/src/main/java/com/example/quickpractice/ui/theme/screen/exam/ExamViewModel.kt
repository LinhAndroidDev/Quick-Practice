package com.example.quickpractice.ui.theme.screen.exam

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor() : ViewModel() {
    private val _questions: MutableStateFlow<List<QuestionModel>?> = MutableStateFlow(null)
    val questions = _questions.asStateFlow()
    private val _durationSeconds: MutableStateFlow<Int> = MutableStateFlow(0)
    val durationSeconds = _durationSeconds.asStateFlow()

    fun getArgument(navController: NavController) {
        val exam = navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<ExamModel>("exam")
        _questions.value = exam?.questions
        _durationSeconds.value = exam?.durationSeconds ?: 0
    }
}