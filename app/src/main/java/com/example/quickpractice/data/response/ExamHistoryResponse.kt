package com.example.quickpractice.data.response

data class ExamHistoryResponse(override val data: ExamResponse.ExamData?) :
    BaseResponse<ExamResponse.ExamData>() {
}