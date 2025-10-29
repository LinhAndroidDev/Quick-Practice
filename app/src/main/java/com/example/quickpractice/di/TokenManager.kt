package com.example.quickpractice.di

import android.content.Context
import com.example.quickpractice.util.SharePreferenceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @Inject
    lateinit var shared: SharePreferenceRepository

    fun saveToken(token: String) {
        shared.saveToken(token)
    }

    fun getToken(): String? = shared.getToken()

    fun clearToken() {
        shared.clearToken()
    }
}