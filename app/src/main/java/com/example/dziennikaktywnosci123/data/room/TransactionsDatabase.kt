package com.example.dziennikaktywnosci123.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dziennikaktywnosci123.data.models.Transaction
import com.example.dziennikaktywnosci123.data.models.User

@Database(entities = [Transaction::class, User::class], version = 2, exportSchema = false)
abstract class TransactionsDatabase: RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDao
    abstract fun userDao(): UserDao

}