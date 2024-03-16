package com.example.thenest_maintrial.data.remote

import com.example.thenest_maintrial.data.remote.model.response.LoginResponse
import com.example.thenest_maintrial.data.remote.model.response.UserResponse
import com.example.thenest_maintrial.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
//    @Headers("Content-Type:application/json")
    @POST("users/register")
    suspend fun postUserDetails(

        @Body user: User
    ): Response<UserResponse>
//    @PATCH("/user/password/reset")
//    suspend fun updateNewPin(
//        @Body pin: String
//    ): UserResponse
//
    @POST("users/login")
    suspend fun loginUser(
        @Body loginDetails: LoginDetails
    ): Response<LoginResponse>
}