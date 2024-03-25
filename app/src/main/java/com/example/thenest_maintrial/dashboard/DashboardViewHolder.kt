package com.example.thenest_maintrial.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenest_maintrial.data.remote.model.response.LlDashboardResponse
import com.example.thenest_maintrial.data.remote.model.response.MaintenanceResponse
import com.example.thenest_maintrial.data.remote.model.response.TenantDashboardResponse
import com.example.thenest_maintrial.repo.userRepository.DashboardRepository
import com.example.thenest_maintrial.utils.Resource
import com.example.thenest_maintrial.utils.parseErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
//class DashboardViewHolder@Inject constructor(
//    private val dashboardRepository: DashboardRepository
//): ViewModel() {
//    fun getLlDashboardDetails() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            val response = dashboardRepository.getLlDashboardDetails()
//            println("Response  from server: ${response.body()}")
//            if (response.isSuccessful) {
//                emit(Resource.success(data = response.body(), message = response.body()?.message))
//            } else {
//                val errorBody = response.errorBody()?.string()?:"Error occurred"
//                val errorMessage = parseErrorMessage(errorBody)
//                emit(Resource.error(message = errorMessage, data = null))
//            }
//        } catch (e: Exception) {
//            emit(Resource.error(e.message ?: "Error Occurred", data = null))
//        }
//    }
//}
class DashboardViewModel@Inject constructor(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {
    private val _dashboardDetails = MutableLiveData<Resource<LlDashboardResponse>>()
    val dashboardDetails: LiveData<Resource<LlDashboardResponse>> = _dashboardDetails

    private val _tenantDashboardDetails = MutableLiveData<Resource<TenantDashboardResponse>>()
    val tenantDashboardDetails: LiveData<Resource<TenantDashboardResponse>> = _tenantDashboardDetails

    private val _maintenanceDetails = MutableLiveData<Resource<MaintenanceResponse>>()
    val maintenanceDetails: LiveData<Resource<MaintenanceResponse>> = _maintenanceDetails

    val maintenanceRvAdapter = MaintenanceRvAdapter(emptyList())


    fun getLlDashboardDetails() {
        viewModelScope.launch {
//            _dashboardDetails.value = Resource.loading(null)
            try {
                val response = dashboardRepository.getLlDashboardDetails()
                println("Response  from server: ${response.body()}")
                if (response.isSuccessful) {
                    _dashboardDetails.value = Resource.success(response.body(), message = response.body()?.message)
                    println("data from server: ${response.body()?.data}")
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Error occurred"
                    val errorMessage = parseErrorMessage(errorBody)
                    _dashboardDetails.value = Resource.error(errorMessage, null)
                }
            } catch (e: Exception) {
                _dashboardDetails.value = Resource.error(e.message ?: "Error Occurred", null)
            }
        }
    }
    fun getTenantDashboardDetails() {
        viewModelScope.launch {
//            _dashboardDetails.value = Resource.loading(null)
            try {
                val response = dashboardRepository.getTenantDashboardDetails()
                println("Response  from server: ${response.body()}")
                if (response.isSuccessful) {
                    _tenantDashboardDetails.value = Resource.success(response.body(), message = response.body()?.message)
                    println("data from server: ${response.body()?.data}")
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Error occurred"
                    val errorMessage = parseErrorMessage(errorBody)
                    _tenantDashboardDetails.value = Resource.error(errorMessage, null)
                }
            } catch (e: Exception) {
                _tenantDashboardDetails.value = Resource.error(e.message ?: "Error Occurred", null)
            }
        }
    }
    fun getMaintenanceRequests() {
        viewModelScope.launch {

            try {
                val response = dashboardRepository.getMaintenanceRequests()
                println("Response  from server: ${response.body()}")
                if (response.isSuccessful) {
                    _maintenanceDetails.value =
                        Resource.success(response.body(), message = response.body()?.message)
                    maintenanceRvAdapter.updateData(response.body()?.data ?: emptyList())
                    println("data from server: ${response.body()?.data}")
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Error occurred"
                    val errorMessage = parseErrorMessage(errorBody)
                    _maintenanceDetails.value = Resource.error(errorMessage, null)
                }
            } catch (e: Exception) {
                _maintenanceDetails.value = Resource.error(e.message ?: "Error Occurred", null)
            }
        }
    }
}