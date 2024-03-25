package com.example.thenest_maintrial.data.remote.model.response

data class MaintenanceResponse (
    val data: List<MaintenanceData>?,
    val message: String?,
)

data class MaintenanceData (
    val unitCode: String,
    val tenantID: String,
    val description: String,
    val isFixed: Boolean,
)