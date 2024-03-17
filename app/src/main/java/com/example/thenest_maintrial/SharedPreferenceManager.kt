package com.example.thenest_maintrial

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(context: Context) {
    private val sharedPreferences:SharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
    fun storeToken(token: String) {
        with(sharedPreferences.edit()) {
            this?.putString("token", token)
            this?.apply()
        }
    }

    fun getToken(): String? {
        return sharedPreferences?.getString("token", null)
    }
}