package com.example.thenest_maintrial.createaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.thenest_maintrial.model.User
import com.example.thenest_maintrial.repo.RegisterRepository
import com.example.thenest_maintrial.repo.userRepository.UserRepository
import com.example.thenest_maintrial.utils.Resource
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
            emit(Resource.success(registerRepository.postUserDetails(user)))
            userRepository.addUser(user)
        }catch (e:Exception){
            emit(Resource.error(e.message?:"Error Occurred",data = null))
        }
    }
}