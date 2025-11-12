package com.example.quickpractice.data.repository

import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import retrofit2.Response

interface ExamHistoryRepository {
    suspend fun getExamHistory(examResultId: Int) : Response<ExamModel>
}