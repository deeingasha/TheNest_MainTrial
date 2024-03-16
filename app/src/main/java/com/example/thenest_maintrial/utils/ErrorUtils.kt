package com.example.thenest_maintrial.utils


import com.google.gson.JsonParser
fun parseErrorMessage(errorBody: String?): String {
    val messageObj = JsonParser.parseString(errorBody).asJsonObject.get("message").asString
    return messageObj ?: "Error occurred"
}