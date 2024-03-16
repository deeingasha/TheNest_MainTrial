package com.example.thenest_maintrial.createaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.thenest_maintrial.model.User
import com.example.thenest_maintrial.repo.RegisterRepository
import com.example.thenest_maintrial.repo.userRepository.UserRepository
import com.example.thenest_maintrial.utils.Resource
import com.example.thenest_maintrial.utils.parseErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel //TODO if it works change everywhere
class CreateAccountViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val registerRepository: RegisterRepository
):ViewModel(){
    fun  saveUserDetails(user:User) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {
            val  response = registerRepository.postUserDetails(user)
            println("Response  from server: ${response.body()}")

            if (response.isSuccessful){
                emit(Resource.success(data = response.body(), message = response.body()?.message))
                userRepository.addUser(user)
            }else{
                val errorBody = response.errorBody()?.string()?:"Error occurred"
                val errorMessage = parseErrorMessage(errorBody)
                emit(Resource.error(message = errorMessage, data = null))
            }
        }catch (e:Exception){
            emit(Resource.error( e.message?:"Error Occurred",data = null))
        } //todo get custom error message
    }
}