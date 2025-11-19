package com.example.quickpractice.ui.theme.screen.exam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.quickpractice.data.repository.ExamRepository
import com.example.quickpractice.ui.theme.screen.exam.argument.ExamArgument
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.ExamResultModel
import com.example.quickpractice.ui.theme.screen.exam.model.ExamType
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel
import com.example.quickpractice.util.SharePreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ApiState {
    class Success(val data: ExamResultModel?) : ApiState()
    class Failure(val message: String) : ApiState()
    class Loading() : ApiState()
    class Idle : ApiState()
}

@HiltViewModel
class ExamViewModel @Inject constructor(private val examRepository: ExamRepository) : ViewModel() {
    private val _questions: MutableStateFlow<List<QuestionModel>?> = MutableStateFlow(null)
    val questions = _questions.asStateFlow()
    private val _durationSeconds: MutableStateFlow<Int> = MutableStateFlow(0)
    val durationSeconds = _durationSeconds.asStateFlow()
    var exam: ExamModel? = null
    private val _state = MutableStateFlow<ApiState>(ApiState.Idle())
    val state = _state.asStateFlow()
    private val _examType: MutableStateFlow<ExamType> = MutableStateFlow(ExamType.PRACTICE)
    val examType = _examType.asStateFlow()
    private val _isSubmitted: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSubmitted = _isSubmitted.asStateFlow()

    @Inject
    lateinit var shared: SharePreferenceRepository

    fun getArgument(navController: NavController) {
        val examArgument = navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<ExamArgument>("exam")
        exam = examArgument?.exam
        _examType.value = examArgument?.type ?: ExamType.PRACTICE
        _questions.value = exam?.questions
        _durationSeconds.value = exam?.durationSeconds ?: 0
    }

    fun submitExam(questions: List<QuestionModel>) = viewModelScope.launch {
        val examResultRequest = QuestionModel.toExamResultRequest(
            examId = exam?.id ?: 0,
            userId = shared.getUserId(),
            questions = questions
        )
        _state.value = ApiState.Loading()
        try {
            val result = examRepository.addExamResult(examResultRequest)
            if (result.isSuccessful) {
                val responseBody = result.body()
                _state.value = ApiState.Success(responseBody)
                _isSubmitted.value = true
            } else {
                _state.value = ApiState.Failure("Failed to submit exam result.")
            }
        } catch (e: Exception) {
            _state.value = ApiState.Failure(e.message.toString())
        }
    }
}