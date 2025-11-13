package com.example.quickpractice.data.response

data class ExamHistoryResponse(override val data: ExamHistoryData?) :
    BaseResponse<ExamHistoryResponse.ExamHistoryData>() {
    data class ExamHistoryData(
        val examResult: ExamResponse.ExamData?
    )
}