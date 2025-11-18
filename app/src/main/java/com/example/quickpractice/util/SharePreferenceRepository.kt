package com.example.quickpractice.util

interface SharePreferenceRepository {
    companion object {
        const val TOKEN = "TOKEN"
        const val USER_NAME = "USER_NAME"
        const val USER_ID = "USER_ID"
    }

    fun saveToken(token: String)

    fun getToken(): String?

    fun clearToken()

    fun saveUserName(userName: String)

    fun getUserName(): String

    fun clearUserName()

    fun saveUserId(userId: Int)

    fun getUserId(): Int

    fun clearUserId()
}