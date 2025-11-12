package com.example.quickpractice.data.repository

import com.example.quickpractice.data.dto.ExamResultRequest
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.ExamResultModel
import retrofit2.Response

interface ExamRepository {
    suspend fun getExams(subjectId: Int): Response<List<ExamModel>?>

    suspend fun addExamResult(examResultRequest: ExamResultRequest): Response<ExamResultModel>
}