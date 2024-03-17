package com.example.thenest_maintrial.utils

import android.content.Context
import com.example.thenest_maintrial.SharedPreferenceManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val context: Context
) :Interceptor{
    private val sharedPreferenceManager = SharedPreferenceManager(context)
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = sharedPreferenceManager.getToken() ?:""

        val request = chain.request()
            .newBuilder().apply {

                addHeader("Authorization", "Bearer $token")
            }.build()

        return chain.proceed(request)
    }
}