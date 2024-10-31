package com.example.dziennikaktywnosci123.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dziennikaktywnosci123.data.models.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users_table WHERE username = :username AND password = :password")
    suspend fun loginUser(username: String, password: String): User?
}