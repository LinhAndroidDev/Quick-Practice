package com.example.quickpractice.data.response

data class RegisterResponse(override val data: RegisterData?) :
    BaseResponse<RegisterResponse.RegisterData>() {

    data class RegisterData(
        val userId: Int?
    )
}