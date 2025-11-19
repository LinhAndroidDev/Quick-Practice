package com.example.quickpractice.data.repository

import com.example.quickpractice.data.dto.LoginRequest
import com.example.quickpractice.data.dto.RegisterRequest
import com.example.quickpractice.data.response.RegisterResponse
import com.example.quickpractice.ui.theme.screen.login.model.LoginModel
import retrofit2.Response

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest) : Response<LoginModel>

    suspend fun register(registerRequest: RegisterRequest) : Response<RegisterResponse>
}