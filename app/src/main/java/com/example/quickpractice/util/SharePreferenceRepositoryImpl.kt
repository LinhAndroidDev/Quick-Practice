package com.example.quickpractice.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharePreferenceRepositoryImpl @Inject constructor(@ApplicationContext ctx: Context) :
    SharePreferenceRepository,
    PreferenceUtil(ctx) {
    private val prefs by lazy { defaultPref() }
    override fun saveToken(token: String) {
        prefs[SharePreferenceRepository.TOKEN] = token
    }

    override fun getToken(): String? {
        return prefs[SharePreferenceRepository.TOKEN, null]
    }

    override fun clearToken() {
        prefs.edit().remove(SharePreferenceRepository.TOKEN).apply()
    }
}