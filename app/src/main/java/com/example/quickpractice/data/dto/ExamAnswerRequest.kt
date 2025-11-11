package com.example.quickpractice.data.dto

data class ExamAnswerRequest(
    val questionId: Int,
    val chosenAnswer: Int,
)