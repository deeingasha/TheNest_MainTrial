package com.example.thenest_maintrial.utils

fun validatePassword(password: String): Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
    val passwordMatcher = Regex(passwordPattern)

    return passwordMatcher.matches(password)
}