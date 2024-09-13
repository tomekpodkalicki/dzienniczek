package com.example.dziennikaktywnosci123.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dziennikaktywnosci123.data.models.Transaction

@Database(entities = [Transaction::class], version = 1, exportSchema = true)
abstract class TransactionsDatabase: RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDao

}