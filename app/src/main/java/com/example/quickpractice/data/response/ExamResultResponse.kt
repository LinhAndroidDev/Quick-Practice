package com.example.quickpractice.data.response

import com.example.quickpractice.ui.theme.screen.exam.model.ExamResultModel

data class ExamResultResponse(override val data: ExamResultData) :
    BaseResponse<ExamResultResponse.ExamResultData>() {
    data class ExamResultData(
        val id: Int,
        val exam: ExamResponse.ExamData,
        val userId: Int,
        val numberCorrectAnswers: Int,
        val numberWrongAnswers: Int,
        val totalQuestions: Int,
        val submittedAt: String
    ) {
        fun toExamResultModel(): ExamResultModel {
            return ExamResultModel(
                id = this.id,
                exam = this.exam.toExamModel(),
                userId = this.userId,
                numberCorrectAnswers = this.numberCorrectAnswers,
                numberWrongAnswers = this.numberWrongAnswers,
                totalQuestions = this.totalQuestions,
                submittedAt = this.submittedAt
            )
        }
    }
}