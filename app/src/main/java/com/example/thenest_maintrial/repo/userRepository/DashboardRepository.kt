package com.example.thenest_maintrial.repo.userRepository

import com.example.thenest_maintrial.data.remote.ApiService
import com.example.thenest_maintrial.data.remote.model.response.LlDashboardResponse
import com.example.thenest_maintrial.data.remote.model.response.MaintenanceResponse
import com.example.thenest_maintrial.data.remote.model.response.TenantDashboardResponse
import retrofit2.Response
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getLlDashboardDetails(): Response<LlDashboardResponse> {
        return api.getLlDashboardDetails()
    }
    suspend fun getTenantDashboardDetails(): Response<TenantDashboardResponse> {
        return api.getTenantDashboardDetails()
    }

    suspend fun getMaintenanceRequests(): Response<MaintenanceResponse> {
        return api.getMaintenanceRequests()
    }

}