package com.example.quickpractice.ui.theme.screen.exam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.quickpractice.data.repository.ExamRepository
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(private val examRepository: ExamRepository) : ViewModel() {
    private val _questions: MutableStateFlow<List<QuestionModel>?> = MutableStateFlow(null)
    val questions = _questions.asStateFlow()
    private val _durationSeconds: MutableStateFlow<Int> = MutableStateFlow(0)
    val durationSeconds = _durationSeconds.asStateFlow()
    var exam: ExamModel? = null
    private val _message: MutableStateFlow<String> = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun getArgument(navController: NavController) {
        exam = navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<ExamModel>("exam")
        _questions.value = exam?.questions
        _durationSeconds.value = exam?.durationSeconds ?: 0
    }

    fun submitExam(questions: List<QuestionModel>) = viewModelScope.launch {
        val examResultRequest = QuestionModel.toExamResultRequest(
            examId = exam?.id ?: 0,
            userId = 3,
            questions = questions
        )
        try {
            val result = examRepository.addExamResult(examResultRequest)
            if (result.isSuccessful) {
                val responseBody = result.body()
                _message.value = responseBody?.message ?: ""
            } else {
                _message.value = "Failed to submit exam result."
            }
        } catch (e: Exception) {
            _message.value = e.message.toString()
        }
    }
}