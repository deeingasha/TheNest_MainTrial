package com.example.thenest_maintrial.repo

import com.example.thenest_maintrial.data.remote.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: ApiService
){
//
//    suspend fun  getOTP(otpRequest: OtpRequest) = api.getOTP(otpRequest )
//
//    suspend fun confirmOTP(otp: OTP) = api.confirmOTP(otp)
//
//    suspend fun updateNewPin(pin:String) = api.updateNewPin(pin)
}