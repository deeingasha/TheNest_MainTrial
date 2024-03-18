package com.example.thenest_maintrial.repo.userRepository

import com.example.thenest_maintrial.data.remote.ApiService
import com.example.thenest_maintrial.data.remote.model.response.LlDashboardResponse
import retrofit2.Response
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getLlDashboardDetails(): Response<LlDashboardResponse> {
        return api.getLlDashboardDetails()
    }
}