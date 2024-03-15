package com.example.thenest_maintrial.data.remote

import com.example.thenest_maintrial.data.remote.model.response.UserResponse
import com.example.thenest_maintrial.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
//    @Headers("Content-Type:application/json")
    @POST("users/register")
    suspend fun postUserDetails(

        @Body user: User
    ): UserResponse
//    @PATCH("/user/password/reset")
//    suspend fun updateNewPin(
//        @Body pin: String
//    ): UserResponse
//
//    @POST("spendy/user/login")
//    suspend fun loginUser(
//        @Body pin: String
//    ): LoginResponse
}