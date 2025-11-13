package com.example.quickpractice.ui.theme.screen.exam_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.quickpractice.data.repository.ExamHistoryRepository
import com.example.quickpractice.ui.theme.navigation.Route
import com.example.quickpractice.ui.theme.screen.exam.argument.ExamArgument
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.ExamResultModel
import com.example.quickpractice.ui.theme.screen.exam.model.ExamType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ApiStateExamHistory {
    class Success(val data: ExamModel?) : ApiStateExamHistory()
    class Failure(val message: String) : ApiStateExamHistory()
    class Loading() : ApiStateExamHistory()
    class Idle : ApiStateExamHistory()
}

@HiltViewModel
class ExamHistoryViewModel @Inject constructor(private val repository: ExamHistoryRepository) : ViewModel() {
    private val _examHistories: MutableStateFlow<List<ExamResultModel>?> = MutableStateFlow(null)
    val examHistories = _examHistories.asStateFlow()
    private val _state = MutableStateFlow<ApiStateExamHistory>(ApiStateExamHistory.Idle())
    val state = _state.asStateFlow()

    suspend fun fetchExamHistories() {
        val response = repository.getExamHistories()
        if (response.isSuccessful) {
            _examHistories.value = response.body()
        } else {
            _examHistories.value = null
        }
    }

    fun fetchExamHistory(navController: NavController, examResultId: Int) = viewModelScope.launch {
        _state.value = ApiStateExamHistory.Loading()
        try {
            val response = repository.getExamHistory(examResultId)
            if (response.isSuccessful) {
                _state.value = ApiStateExamHistory.Success(response.body())
                if (response.body() != null) {
                    goToExam(navController, response.body()!!)
                }
            } else {
                _state.value = ApiStateExamHistory.Failure("Failed to fetch exam history.")
            }
        } catch (e: Exception) {
            _state.value = ApiStateExamHistory.Failure(e.message.toString())
        } finally {
            _state.value = ApiStateExamHistory.Idle()
        }
    }

    private fun goToExam(navController: NavController, exam: ExamModel) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.set("exam", ExamArgument(exam, ExamType.HISTORY))
        navController.navigate(Route.EXAM.route)
    }
}