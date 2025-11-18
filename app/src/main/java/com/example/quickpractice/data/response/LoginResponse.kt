package com.example.quickpractice.data.response

import com.example.quickpractice.ui.theme.screen.login.model.LoginModel

data class LoginResponse(override val data: LoginData?) : BaseResponse<LoginResponse.LoginData>() {

    data class LoginData(
        val userId: Int?,
        val username: String?,
        val role: Int?,
        val auth: AuthResponse?,
    ) {
        fun toLoginModel(): LoginModel {
            return LoginModel(
                userId = userId,
                username = username,
                role = role,
                auth = auth?.let {
                    LoginModel.AuthModel(
                        accessToken = it.accessToken
                    )
                }
            )
        }
    }

    data class AuthResponse(
        val accessToken: String?,
    )
}