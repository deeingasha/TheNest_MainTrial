package com.example.thenest_maintrial.repo

import com.example.thenest_maintrial.data.remote.ApiService
import com.example.thenest_maintrial.data.remote.LoginDetails
import com.example.thenest_maintrial.data.remote.model.response.LoginResponse
import com.example.thenest_maintrial.data.remote.model.response.UserResponse
import com.example.thenest_maintrial.model.User
import retrofit2.Response
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val api: ApiService
){
//    suspend fun postUserDetails(user: User) = api.postUserDetails(user)
//suspend fun postUserDetails(user: User) = api.postUserDetails(user)
suspend fun postUserDetails(user: User): Response<UserResponse> {
    return api.postUserDetails(user)
}
    suspend fun loginUser(loginDetails: LoginDetails):Response<LoginResponse> {
        return api.loginUser(loginDetails)
    }
}