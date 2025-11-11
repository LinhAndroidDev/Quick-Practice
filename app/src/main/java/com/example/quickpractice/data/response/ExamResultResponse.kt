package com.example.quickpractice.data.response

data class ExamResultResponse(override val data: ExamResultData) :
    BaseResponse<ExamResultResponse.ExamResultData>() {
    data class ExamResultData(
        val id: Int,
        val exam: ExamResponse,
        val userId: Int,
        val numberCorrectAnswers: Int,
        val totalQuestions: Int,
        val submittedAt: String
    )
}