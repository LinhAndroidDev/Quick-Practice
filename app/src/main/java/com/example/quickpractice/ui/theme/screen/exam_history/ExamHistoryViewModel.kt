package com.example.quickpractice.ui.theme.screen.exam_history

import androidx.lifecycle.ViewModel
import com.example.quickpractice.data.repository.ExamHistoryRepository
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.ExamResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExamHistoryViewModel @Inject constructor(private val repository: ExamHistoryRepository) : ViewModel() {
    private val _examHistories: MutableStateFlow<List<ExamResultModel>?> = MutableStateFlow(null)
    val examHistories = _examHistories.asStateFlow()

    suspend fun fetchExamHistories() {
        val response = repository.getExamHistories()
        if (response.isSuccessful) {
            _examHistories.value = response.body()
        } else {
            _examHistories.value = null
        }
    }
}