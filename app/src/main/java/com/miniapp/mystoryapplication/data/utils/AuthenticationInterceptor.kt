package com.miniapp.mystoryapplication.data.utils

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthenticationInterceptor(private val preferences: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.toString()
        val requestBuilder = chain.request().newBuilder()

        when {
            url.isBearerAuthentication() -> {
                requestBuilder.addBearerAuthorization()
            }
        }

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }

    private fun String.isBearerAuthentication(): Boolean {
        val bearerAuthenticationUrl = listOf("stories")

        bearerAuthenticationUrl.forEach {
            if (this.contains(it)) return true
        }

        return false
    }

    private fun Request.Builder.addBearerAuthorization(): Request.Builder {
        val token = preferences.getString(PREFERENCES_ITEM_USER_TOKEN, null)
        return this.addHeader("Authorization", "Bearer $token")
    }

    companion object {
        const val PREFERENCES_ITEM_USER_TOKEN = "token"
    }
}