package com.example.thenest_maintrial.repo.userRepository

import com.example.thenest_maintrial.data.local.UserDao
import com.example.thenest_maintrial.model.User

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun addUser(user: User) {
        userDao.addUser(user)
    }
}