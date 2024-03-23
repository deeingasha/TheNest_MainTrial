package com.example.thenest_maintrial.properties

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.thenest_maintrial.data.remote.PropertyDetails
import com.example.thenest_maintrial.data.remote.model.response.LL_PropertiesResponse
import com.example.thenest_maintrial.repo.PropertiesRepository
import com.example.thenest_maintrial.utils.Resource
import com.example.thenest_maintrial.utils.SingleLiveEvent
import com.example.thenest_maintrial.utils.parseErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertiesViewModel @Inject constructor(
    private val propertiesRepository: PropertiesRepository
) : ViewModel(){
    private val _llProperties = MutableLiveData<Resource<LL_PropertiesResponse>>()
    val llProperties: LiveData<Resource<LL_PropertiesResponse>> = _llProperties

    //livedata object to hold the position of the newly added property
    private val _newPropertyPosition = SingleLiveEvent<Int>()
    val newPropertyPosition: LiveData<Int> = _newPropertyPosition

    //create an instance of the adapter
    val propertiesRvAdapter = PropertiesRvAdapter(emptyList())

    fun getLLProperties() {
       viewModelScope.launch {
           try {
               val response= propertiesRepository.getLlProperties()
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

    fun addProperty(propertyDetails: PropertyDetails) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = propertiesRepository.addProperty(propertyDetails)
            println("Response  from server: ${response.body()}")
            if (response.isSuccessful) {
                emit(Resource.success(data = response.body(), message = response.body()?.message))

                //update the position of the newly added property
                _newPropertyPosition.postValue(propertiesRvAdapter.itemCount-1)
            } else {
                val errorBody = response.errorBody()?.string()?:"Error occurred"
                val errorMessage = parseErrorMessage(errorBody)
                emit(Resource.error(message = errorMessage, data = null))
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Error Occurred", data = null))
        }
    }

    fun getSingleProperty(propertyId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = propertiesRepository.getSingleProperty(propertyId)
            println("Response from server: ${response.body()}")
            if (response.isSuccessful) {
                emit(Resource.success(data = response.body(), message = response.body()?.message))
            } else {
                val errorBody = response.errorBody()?.string()?: "Error Occurred"
                val errorMessage = parseErrorMessage(errorBody)
                emit(Resource.error(message = errorMessage, data = null))
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Error Occurred", data = null))
        }
    }


}
class PropertiesViewModelFactory(private val repository: PropertiesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertiesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PropertiesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}