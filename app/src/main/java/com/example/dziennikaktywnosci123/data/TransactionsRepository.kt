package com.example.dziennikaktywnosci123.data

import android.content.Context
import com.example.dziennikaktywnosci123.data.models.Transaction
import com.example.dziennikaktywnosci123.data.room.DatabaseInstance

class TransactionsRepository(context: Context) {

    private val transactionsDao = DatabaseInstance.getInstance(context).transactionsDao()

    suspend fun insertTransaction(transaction: Transaction) {
        transactionsDao.insertTransaction(transaction)
    }
    suspend fun updateTransaction(transaction: Transaction) {
        transactionsDao.updateTransaction(transaction)
    }

    suspend fun deleteTransaction(transactions: List<Transaction>) {
        transactionsDao.deleteTransaction(transactions)
    }

    fun getAllTransactions() = transactionsDao.getAllTransactions()
    fun getAllIncomes() = transactionsDao.getAllIncomes()
    fun getAllOutcomes() = transactionsDao.getAllOutcomes()
    fun getSumOfIncomeGroupByCategory() = transactionsDao.getSumOfIncomeGroupByCategory()
    fun getSumOfOutcomeGroupByCategory() = transactionsDao.getSumOfOutcomeGroupByCategory()
}