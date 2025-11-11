package com.example.quickpractice.data.dto

data class ExamResultRequest(
    val examId: Int,
    val userId: Int,
    val numberCorrectAnswers: Int,
    val numberWrongAnswers: Int,
    val totalQuestions: Int,
    val examAnswers: List<ExamAnswerRequest>,
)