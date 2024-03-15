package com.example.thenest_maintrial.repo

import com.example.thenest_maintrial.data.remote.ApiService
import com.example.thenest_maintrial.model.User
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val api: ApiService
){
//    suspend fun postUserDetails(user: User) = api.postUserDetails(user)
suspend fun postUserDetails(user: User) = api.postUserDetails(user)


// TODO: login 
//    suspend fun loginUser(pin: String) = api.loginUser(pin)
}