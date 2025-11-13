package com.example.quickpractice.data.response

data class ListExamHistoryResponse(override val data: List<ExamResultResponse.ExamResultData>?) :
    BaseResponse<List<ExamResultResponse.ExamResultData>>() {
}