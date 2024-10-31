package com.example.dziennikaktywnosci123.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,
    val username: String,
    val password: String
)
