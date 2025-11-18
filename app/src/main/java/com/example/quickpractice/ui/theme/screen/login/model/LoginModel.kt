package com.example.quickpractice.ui.theme.screen.login.model

data class LoginModel(
    val userId: Int?,
    val username: String?,
    val role: Int?,
    val auth: AuthModel?,
) {
    data class AuthModel(
        val accessToken: String?,
    )
}