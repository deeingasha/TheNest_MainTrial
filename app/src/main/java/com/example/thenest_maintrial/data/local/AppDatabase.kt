package com.example.thenest_maintrial.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.spenndify.data.local.UserDao
import com.example.thenest_maintrial.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getUserDao(): UserDao
}
