package com.example.thenest_maintrial.repo.userRepository

import com.example.thenest_maintrial.model.User

interface UserRepository {
    suspend fun addUser(user: User)

}