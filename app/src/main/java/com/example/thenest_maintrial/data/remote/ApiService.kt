package com.example.thenest_maintrial.data.remote

import com.example.thenest_maintrial.data.remote.model.response.ForgotPinResponse
import com.example.thenest_maintrial.data.remote.model.response.LL_PropertiesResponse
import com.example.thenest_maintrial.data.remote.model.response.LlDashboardResponse
import com.example.thenest_maintrial.data.remote.model.response.LoginResponse
import com.example.thenest_maintrial.data.remote.model.response.UserResponse
import com.example.thenest_maintrial.data.remote.model.response.addPropertyResponse
import com.example.thenest_maintrial.data.remote.model.response.singlePropertyResponse
import com.example.thenest_maintrial.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
//    @Headers("Content-Type:application/json")
    @POST("users/register")
    suspend fun postUserDetails(

        @Body user: User
    ): Response<UserResponse>
    @PATCH("users/reset-password")
    suspend fun resetPassword(
        @Body loginDetails: LoginDetails
    ): Response<ForgotPinResponse>

    @POST("users/login")
    suspend fun loginUser(
        @Body loginDetails: LoginDetails
    ): Response<LoginResponse>

    @GET("dashboard/landlord")
    suspend fun getLlDashboardDetails(): Response<LlDashboardResponse>

    @GET("properties/landlord")
    suspend fun getLlProperties(): Response<LL_PropertiesResponse>

    @POST("properties/register")
    suspend fun addProperty(
        @Body   propertyDetails: PropertyDetails
    ):Response<addPropertyResponse>

   @GET("properties/{propertyId}")
   suspend fun getSingleProperty(
       @Path("propertyId") propertyId: String
   ): Response<singlePropertyResponse>

}