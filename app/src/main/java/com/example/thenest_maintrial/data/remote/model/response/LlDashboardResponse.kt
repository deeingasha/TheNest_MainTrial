package com.example.thenest_maintrial.data.remote.model.response

data class LlDashboardResponse(
    val data: LlDashboardDetails?,
    val message: String

)

data class LlDashboardDetails(
    val name: String,
    val numOfProperties: Int,
    val totalVacantUnits: Int,
    val numOfTenants: Int
)

