package com.miniapp.mystoryapplication.data.source

import android.content.SharedPreferences

class PreferencesDataSource(private val preferences: SharedPreferences) {

    private val editor: SharedPreferences.Editor = preferences.edit()

    fun loggedInUserId(userId: String) {
        editor.run {
            putString(PREFERENCES_ITEM_USER_ID, userId)
            commit()
        }
    }

    fun loggedInUserId(): String = preferences.getString(PREFERENCES_ITEM_USER_ID, "") ?: ""

    fun loggedInUserName(username: String) {
        editor.run {
            putString(PREFERENCES_ITEM_USER_NAME, username)
            commit()
        }
    }

    fun loggedInUserName(): String = preferences.getString(PREFERENCES_ITEM_USER_NAME, "") ?: ""


    fun loggedInToken(token: String) {
        editor.run {
            //"Bearer $token"
            putString(PREFERENCES_ITEM_USER_TOKEN, token)
            commit()
        }
    }

    fun loggedInToken(): String = preferences.getString(PREFERENCES_ITEM_USER_TOKEN, "") ?: ""

    fun logout() {
        editor.run {
            clear()
            commit()
        }
    }

    companion object {
        const val PREFERENCES_ITEM_USER_ID = "user_id"
        const val PREFERENCES_ITEM_USER_NAME = "user_name"
        const val PREFERENCES_ITEM_USER_TOKEN = "token"
    }
}