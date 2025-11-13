package com.example.quickpractice.data.repository

import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.ExamResultModel
import retrofit2.Response

interface ExamHistoryRepository {
    suspend fun getExamHistories() : Response<List<ExamResultModel>>

    suspend fun getExamHistory(examResultId: Int) : Response<ExamModel>
}