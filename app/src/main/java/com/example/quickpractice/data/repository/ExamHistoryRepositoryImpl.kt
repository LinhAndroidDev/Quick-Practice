package com.example.quickpractice.data.repository

import com.example.quickpractice.data.ApiService
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.ExamResultModel
import retrofit2.Response
import javax.inject.Inject

class ExamHistoryRepositoryImpl @Inject constructor(private val apiService: ApiService) : ExamHistoryRepository {
    override suspend fun getExamHistories(): Response<List<ExamResultModel>> {
        val examHistoriesResponse = apiService.getExamHistories()
        val examHistories = examHistoriesResponse.body()?.data?.map { it.toExamResultModel() }
        return Response.success(examHistories)
    }

    override suspend fun getExamHistory(examResultId: Int): Response<ExamModel> {
        val examHistoryResponse = apiService.getExamHistory(examResultId)
        val examHistory = examHistoryResponse.body()?.data?.toExamModel()
        return Response.success(examHistory)
    }

}