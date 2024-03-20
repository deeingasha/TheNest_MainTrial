package com.example.thenest_maintrial.properties

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenest_maintrial.data.remote.model.response.LL_PropertiesResponse
import com.example.thenest_maintrial.repo.userRepository.DashboardRepository
import com.example.thenest_maintrial.utils.Resource
import com.example.thenest_maintrial.utils.parseErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertiesViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository
) : ViewModel(){
    private val _llProperties = MutableLiveData<Resource<LL_PropertiesResponse>>()
    val llProperties: LiveData<Resource<LL_PropertiesResponse>> = _llProperties

    //create an instance of the adapter
    val propertiesRvAdapter = PropertiesRvAdapter(emptyList())

    fun getLLProperties() {
       viewModelScope.launch {
           try {
               val response= dashboardRepository.getLlProperties()
               println("Response from server VM: ${response.body()}")
               if (response.isSuccessful) {
                   _llProperties.value = Resource.success(response.body(), message = response.body()?.message)
                   propertiesRvAdapter.updateData(response.body()?.data?: emptyList())
                   println("data from success: ${response.body()?.data}")
               } else {
                   val errorBody = response.errorBody()?.toString()?: "Error Occurred"
                   val errorMessage = parseErrorMessage(errorBody)
                   _llProperties.value = Resource.error(errorMessage, null)
               }
           }catch (e: Exception) {
               _llProperties.value = Resource.error(e.message?:"Error Occurred"  ,null)
           }
       }
    }
}