package com.example.dziennikaktywnosci123.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dziennikaktywnosci123.data.models.CategoryTotal
import com.example.dziennikaktywnosci123.data.models.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {
    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transactions: List<Transaction>)

    @Query("SELECT * FROM transactions_table ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions_table WHERE type = 'PRZYCHÓD'")
    fun getAllIncomes(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions_table WHERE type = 'WYDATEK'")
    fun getAllOutcomes(): Flow<List<Transaction>>

    @Query("SELECT category, SUM(price) as total FROM transactions_table WHERE type = 'PRZYCHÓD' GROUP BY category")
    fun getSumOfIncomeGroupByCategory(): Flow<List<CategoryTotal>>

    @Query("SELECT category, SUM(price) as total FROM transactions_table WHERE type = 'WYDATEK' GROUP BY category")
    fun getSumOfOutcomeGroupByCategory(): Flow<List<CategoryTotal>>

    @Query("SELECT * FROM transactions_table WHERE userId = :userId")
    fun getTransactionsForUser(userId: Int): LiveData<List<Transaction>>
}
