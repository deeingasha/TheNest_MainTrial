package com.example.thenest_maintrial.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int = 0,

    @ColumnInfo(name = "fName")
    var fName: String,

    @ColumnInfo(name = "lName")
    var lName: String,

    @ColumnInfo(name = "idNumber")
    var idNumber: String,


    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "phoneNo")
    var phoneNo: String,

    @ColumnInfo(name = "password")
    var password: String,
)
