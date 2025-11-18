package com.example.quickpractice.data.repository

import com.example.quickpractice.data.ApiService
import com.example.quickpractice.data.dto.LoginRequest
import com.example.quickpractice.ui.theme.screen.login.model.LoginModel
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val apiService: ApiService) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Response<LoginModel> {
        val loginResponse = apiService.login(loginRequest)
        val loginModel = loginResponse.body()?.data?.toLoginModel()
        return Response.success(loginModel)
    }

}