package com.example.thenest_maintrial.data.remote.model.response

data class UserResponse(
    val data: UserReturned?,
    val message:String,
)

data class UserReturned(
    val fName: String,
    val lName: String,
    val idNumber: String,
    val email: String,
    val phoneNo: String,
    val password: String,
)