package com.example.quickpractice.data.repository

import com.example.quickpractice.data.ApiService
import com.example.quickpractice.data.dto.LoginRequest
import com.example.quickpractice.data.dto.RegisterRequest
import com.example.quickpractice.data.response.RegisterResponse
import com.example.quickpractice.ui.theme.screen.login.model.LoginModel
import com.example.quickpractice.util.ApiStatus
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val apiService: ApiService) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Response<LoginModel> {
        val loginResponse = apiService.login(loginRequest)
        val loginModel = loginResponse.body()?.data?.toLoginModel()
        val status = loginResponse.body()?.status
        return if (status == ApiStatus.SUCCESS.value) {
            Response.success(loginModel)
        } else {
            val message = loginResponse.body()?.message ?: "Unknown error"
            val errorBody = message.toResponseBody("text/plain".toMediaType())
            Response.error(loginResponse.body()?.status ?: ApiStatus.FAILURE.value, errorBody)
        }
    }

    override suspend fun register(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return apiService.register(registerRequest)
    }

}