package com.example.thenest_maintrial.tenants

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenest_maintrial.data.remote.model.response.LL_TenantsResponse
import com.example.thenest_maintrial.repo.TenantsRepository
import com.example.thenest_maintrial.utils.Resource
import com.example.thenest_maintrial.utils.parseErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TenantsViewModel @Inject constructor(
    private val tenantsRepository: TenantsRepository
): ViewModel(){
    private val _llTenants = MutableLiveData<Resource<LL_TenantsResponse>>()
    val llTenants: MutableLiveData<Resource<LL_TenantsResponse>> = _llTenants

    //create an instance of the adapter
    val tenantRvAdapter = TenantRvAdapter(emptyList())

    fun getLLTenants() {
        viewModelScope.launch {
            try {
                _llTenants.value = Resource.loading(null)
                val response = tenantsRepository.getLlTenants()
                println("Response from server tt: ${response.body()}")
                    if (response.isSuccessful) {
                        _llTenants.value = Resource.success(response.body(), message =  response.body()?.message)
                        tenantRvAdapter.updateData(response.body()?.data?: emptyList())

                    } else {
                        val errorBody = response.errorBody()?.toString()?: "Error Occurred"
                        val errorMessage = parseErrorMessage(errorBody)
                        _llTenants.value = Resource.error(errorMessage, null)
                    }

            } catch (e: Exception) {
                _llTenants.value = Resource.error(e.message?:"Error Occurred", null)
            }
        }
    }
}