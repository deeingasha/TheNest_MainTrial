package com.example.thenest_maintrial.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thenest_maintrial.data.remote.model.response.LL_PropertiesResponse

class SharedViewModel : ViewModel() {
    val llProperties: MutableLiveData<Resource<LL_PropertiesResponse>> = MutableLiveData()
}