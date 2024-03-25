package com.example.thenest_maintrial.repo

import com.example.thenest_maintrial.data.remote.ApiService
import com.example.thenest_maintrial.data.remote.model.response.LL_TenantsResponse
import retrofit2.Response
import javax.inject.Inject

class TenantsRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getLlTenants() : Response<LL_TenantsResponse> {
        return api.getLlTenants()
    }
}