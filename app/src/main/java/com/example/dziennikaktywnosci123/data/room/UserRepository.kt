package com.example.dziennikaktywnosci123.data.room

import android.content.Context
import com.example.dziennikaktywnosci123.data.models.User

class UserRepository(context: Context) {
    private val userDao = DatabaseInstance.getInstance(context).userDao()

    suspend fun registerUser(user: User) = userDao.insertUser(user)

    suspend fun loginUser(username: String, password: String): User? = userDao.loginUser(username, password)
}