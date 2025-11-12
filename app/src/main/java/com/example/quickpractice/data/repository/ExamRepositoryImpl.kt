package com.example.quickpractice.data.repository

import com.example.quickpractice.data.ApiService
import com.example.quickpractice.data.dto.ExamResultRequest
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.ExamResultModel
import retrofit2.Response
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(private val apiService: ApiService) : ExamRepository {
    override suspend fun getExams(subjectId: Int): Response<List<ExamModel>?> {
        val examResponse = apiService.getExams(subjectId)
        val exam = examResponse.body()?.data?.map { e -> e.toExamModel() }
        return Response.success(exam)
    }

    override suspend fun addExamResult(examResultRequest: ExamResultRequest): Response<ExamResultModel> {
        val examResultResponse = apiService.addExamResult(examResultRequest)
        val examResult = examResultResponse.body()?.data?.toExamResultModel()
        return Response.success(examResult)
    }
}