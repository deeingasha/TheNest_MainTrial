package com.example.thenest_maintrial.data.remote.model.response

data class LL_TenantsResponse(
    val data: List<LL_Tenant>,
    val message: String,
)

data class LL_Tenant(
    val idNumber: String,
    val is_active: Boolean,
    val unitCode: String,
    val start_date: String,
    val end_date: String,
    val userDetails: TenantDetails,
)