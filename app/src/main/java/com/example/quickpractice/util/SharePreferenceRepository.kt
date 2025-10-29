package com.example.quickpractice.util

interface SharePreferenceRepository {
    companion object {
        const val TOKEN = "TOKEN"
    }

    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}