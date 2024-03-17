package com.example.thenest_maintrial.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.thenest_maintrial.data.remote.LoginDetails
import com.example.thenest_maintrial.repo.RegisterRepository
import com.example.thenest_maintrial.utils.Resource
import com.example.thenest_maintrial.utils.parseErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ForgotPinViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
): ViewModel() {
//todo: change to so that you authenticate the user before sending the reset password request e.g via email or phone number
    fun resetPassword(loginDetails: LoginDetails) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = registerRepository.resetPassword(loginDetails)
            println("Response  from server: ${response.body()}")
            if (response.isSuccessful) {
                emit(Resource.success(data = response.body(), message = response.body()?.message))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Error occurred"
                val errorMessage = parseErrorMessage(errorBody)
                emit(Resource.error(message = errorMessage, data = null))
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Error Occurred", data = null))
        }

    }
}