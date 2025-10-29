package com.example.quickpractice.data.response

data class SubjectResponse(override val data: List<SubjectData>?) :
    BaseResponse<List<SubjectResponse.SubjectData>?>() {
    class SubjectData(
        val id: Int,
        val nameSubject: String,
    )
}