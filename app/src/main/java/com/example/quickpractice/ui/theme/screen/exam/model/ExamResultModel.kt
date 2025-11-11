package com.example.quickpractice.ui.theme.screen.exam.model

data class ExamResultModel(
    val id: Int,
    val exam: ExamModel,
    val userId: Int,
    val numberCorrectAnswers: Int,
    val totalQuestions: Int,
    val submittedAt: String
)