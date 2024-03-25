package com.example.thenest_maintrial.repo

import com.example.thenest_maintrial.data.remote.ApiService
import com.example.thenest_maintrial.data.remote.PropertyDetails
import com.example.thenest_maintrial.data.remote.model.response.LL_PropertiesResponse
import com.example.thenest_maintrial.data.remote.model.response.PropReportResponse
import com.example.thenest_maintrial.data.remote.model.response.addPropertyResponse
import com.example.thenest_maintrial.data.remote.model.response.singlePropertyResponse
import retrofit2.Response
import javax.inject.Inject

class PropertiesRepository    @Inject constructor(
    private val api: ApiService
) {
    suspend fun getLlProperties(): Response<LL_PropertiesResponse> {
        return api.getLlProperties()
    }

    suspend fun addProperty(propertyDetails: PropertyDetails):Response<addPropertyResponse>{
        return api.addProperty(propertyDetails)
    }

    suspend fun getSingleProperty(propertyId: String): Response<singlePropertyResponse> {
        return api.getSingleProperty(propertyId)
    }

    suspend fun createLandlordPropertiesReport(
        filter: String?,
        sort: String?,
        search: String?,
        pdf: Boolean?,
    ): Response<PropReportResponse> {
        return api.createLandlordPropertiesReport(filter, sort, search, pdf)
    }

}