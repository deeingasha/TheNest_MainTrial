//package com.example.spenndify.createaccount
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.liveData
//import com.example.spenndify.model.User
//import com.example.spenndify.repo.RegisterRepository
//import com.example.spenndify.repo.userRepository.UserRepository
//import com.example.spenndify.utils.Resource
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import javax.inject.Inject
//
//@HiltViewModel //TODO if it works change everywhere
//class CreateAccountViewModel @Inject constructor(
//    private val userRepository: UserRepository,
//    private val registerRepository: RegisterRepository
//):ViewModel(){
//    fun  saveUserDetails(user:User) = liveData(Dispatchers.IO) {
//
//        emit(Resource.loading(data = null))
//
//        try {
//            emit(Resource.success(registerRepository.postUserDetails(user)))
//            userRepository.addUser(user)
//        }catch (e:Exception){
//            emit(Resource.error(e.message?:"Error Occurred",data = null))
//        }
//    }
//}