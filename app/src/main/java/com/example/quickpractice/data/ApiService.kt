package com.example.quickpractice.data

import com.example.quickpractice.data.response.ExamResponse
import com.example.quickpractice.data.response.SubjectResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("subject/get_subjects")
    suspend fun getSubjects(): Response<SubjectResponse>

    @GET("exam/get_exams")
    suspend fun getExams(
        @Query("subjectId") subjectId: Int
    ): Response<ExamResponse>
}