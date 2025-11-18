package com.example.quickpractice.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit

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
        prefs.edit { remove(SharePreferenceRepository.TOKEN) }
    }

    override fun saveUserName(userName: String) {
        prefs[SharePreferenceRepository.USER_NAME] = userName
    }

    override fun getUserName(): String {
        return prefs[SharePreferenceRepository.USER_NAME, ""] ?: ""
    }

    override fun clearUserName() {
        prefs.edit { remove(SharePreferenceRepository.USER_NAME) }
    }

    override fun saveUserId(userId: Int) {
        prefs[SharePreferenceRepository.USER_ID] = userId
    }

    override fun getUserId(): Int {
        return prefs[SharePreferenceRepository.USER_ID, -1] ?: -1
    }

    override fun clearUserId() {
        prefs.edit { remove(SharePreferenceRepository.USER_ID) }
    }
}