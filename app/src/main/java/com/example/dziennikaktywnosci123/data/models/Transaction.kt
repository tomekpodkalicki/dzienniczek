package com.example.dziennikaktywnosci123.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions_table")
data class Transaction(
   @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,
    val date: Long,
    val price: Float,
    val type: TransactionType,
    val desc: String,
    val category: TransactionCategory


)